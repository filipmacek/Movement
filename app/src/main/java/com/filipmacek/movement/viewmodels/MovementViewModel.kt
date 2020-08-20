package com.filipmacek.movement.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.filipmacek.movement.blockchain.SmartContractAgent
import com.filipmacek.movement.data.location.Coordinate
import com.filipmacek.movement.data.location.LocationRepository
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.nodes.NodeRepository
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.routes.RouteRepository
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserRepository
import com.filipmacek.movement.workers.RouteFinishedWorker
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MovementViewModel(private val routeRepository: RouteRepository,
                        private val locationRepository: LocationRepository,
                        private val nodeRepository: NodeRepository,
                        private val smartContractAgent: SmartContractAgent,
                        private val userRepository: UserRepository
                        ) :ViewModel() {


    private val compositeDisposable = CompositeDisposable()


    // Route object
    var route:Route? =null
    // Node object
    var nodes:List<Node>?= null
    // User object
    var user: User? = null


    // Start && end location
    var startLocation:Coordinate? =null
    var endLocation:Coordinate?= null
    private val threshHold:Int = 20

    val nodeStatusData:ArrayList<BehaviorSubject<Int>> = arrayListOf()
    val nodeStatusConnection:ArrayList<BehaviorSubject<Boolean>> = arrayListOf()

    // Timer
    val timerInterval = Observable.interval(1000L,TimeUnit.MILLISECONDS).timeInterval().observeOn(Schedulers.computation())

    // Last two coordinates for calculating distance
    val pointLast:BehaviorSubject<Coordinate> = BehaviorSubject.create( )
    val pointPreLast:BehaviorSubject<Coordinate> = BehaviorSubject.create()

    // Distance
    val distanceKm:BehaviorSubject<Double> = BehaviorSubject.create()
    val distanceM:BehaviorSubject<Double> = BehaviorSubject.create()

    // Speed
    val speedKmh:BehaviorSubject<Double> = BehaviorSubject.create()
    val speedMs:BehaviorSubject<Double> = BehaviorSubject.create()

    // Start && end location status
    val startLocationStatus:BehaviorSubject<Boolean> = BehaviorSubject.create()
    val endLocationStatus:BehaviorSubject<Boolean> = BehaviorSubject.create()



    val mainComputationChecker= locationRepository.getLastCoordinate().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe{coordinate ->
        // Check first
        // Put last
        var current_last:Coordinate?=null
        if(pointLast.value == null){
            current_last = coordinate
        }else{
            current_last = pointLast.value
        }
        pointLast.onNext(coordinate)
        pointPreLast.onNext(current_last!!)

        // Start location check
        if(startLocation?.checkIfNearby(coordinate,threshHold)!!) {
            startLocationStatus.onNext(true)
        }
        // End location check
        if(endLocation?.checkIfNearby(coordinate,threshHold)!!) {
        endLocationStatus.onNext(true) }

        // Distance (route started and we have new location point so we have to change location)
        if(startLocationStatus.value!!){
            val current_distanceM=distanceM.value
            val current_distanceKm=distanceKm.value
            val distance = pointPreLast.value!!.getDistance(pointLast.value!!)
            distanceM.onNext(current_distanceM!! +distance)
            distanceKm.onNext(current_distanceKm!! +distance/1000)
        }

        // Current speed
        speedKmh.onNext(pointLast.value!!.getVelocityKmh(pointPreLast.value!!))
        speedMs.onNext(pointLast.value!!.getVelocityMs(pointPreLast.value!!))

        // Post it to nodes
        nodes?.mapIndexed { index,node ->
            nodeRepository.postDataToNode(node, route?.routeId!!,coordinate).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(
                    {
                        // OnNext --- Call completed-data has been sent to node
                        Log.i(TAG,"Sent!!!Successful POST data request to node "+node.nodeName)
                        nodeStatusData[index].onNext(nodeStatusData[index].value!! +1)
                        nodeStatusConnection[index].onNext(true)
                    },
                    {
                        Log.i(TAG,"Error sending data to node "+node.nodeName)
                        nodeStatusConnection[index].onNext(false)
                    })
        }



    }.addTo(compositeDisposable)



    // ========== Smart contract agent =============

    val smartContractAgentRouteStarts = locationRepository.getLastCoordinate()
            .subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).take(1)
            .subscribe({
                // After first location received Smart Contract agent will notify smart contract
                // on blockchain that route started and everything is fine with location sensor
                //smartContractAgent.routeStarted(this.user!!, this.route!!,this.nodes!!)
            },{ error -> Log.i(TAG,"Error! SmartContract Agent could NOT start startRouteEvent on smart contract")}).addTo(compositeDisposable)




        fun initViewModel(routeId:String,username:String){
            route = routeRepository.getRouteById(routeId)
            user = userRepository.getUserByUsername(username)

            startLocation = Coordinate("0",getLoc(route!!.startLocation,0),getLoc(route!!.startLocation,1),0)
            endLocation = Coordinate("0",getLoc(route!!.endLocation,0),getLoc(route!!.endLocation,1),0)

            // Start and end location status
            startLocationStatus.onNext(false)
            endLocationStatus.onNext(false)

            // Distance
            distanceKm.onNext(0.0)
            distanceM.onNext(0.0)

            //Speed
            speedMs.onNext(0.0)
            speedKmh.onNext(0.0)

            // Nodes
            nodes = nodeRepository.getNodesSync()

            nodes!!.map {
                nodeStatusData.add(BehaviorSubject.create())
                nodeStatusConnection.add(BehaviorSubject.create())
            }
            nodeStatusData.map { it.onNext(0) }
            nodeStatusConnection.map{it.onNext(false)}
            clearDataNodes()

        }


        fun routeById(routeId:String):Route = routeRepository.getRouteById(routeId)

        fun clearDatabase() = locationRepository.clearAll()

        private fun clearDataNodes() {
            nodes!!.map {node: Node ->
                nodeRepository.clearRouteData(node.ip,node.dataEndpoint, route?.routeId!!).subscribeOn(Schedulers.io()).subscribe(
                        {Log.i(TAG,"Data cleared for node "+node.nodeName)},
                        {Log.i(TAG,"Data NOT cleared for node "+node.nodeName)}
                )
            }
        }





        private fun getLoc(s:String,t:Int):Double {
            if(t==0)  {
                return (s.substring(0,s.indexOf(",")).toDouble())
            } else {
                return (s.substring(s.indexOf(",")+1).toDouble())
            }

        }

        override fun onCleared() {
            super.onCleared()
            Log.i(TAG,"Clearing MovementViewModel")
            compositeDisposable.clear()
        }


        companion object{
        const val TAG="MovementViewModel"
    }




}