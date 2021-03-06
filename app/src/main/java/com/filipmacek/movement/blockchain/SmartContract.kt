package com.filipmacek.movement.blockchain

import android.annotation.SuppressLint
import android.util.Log
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.users.User
import org.web3j.protocol.Web3j
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import com.filipmacek.movement.BuildConfig
import io.reactivex.disposables.CompositeDisposable


class SmartContract(web3j: Web3j){
    private val compositeDisposable:CompositeDisposable = CompositeDisposable()
    private val address=BuildConfig.CONTRACT_ADDRESS
    private val contractManager: ReadonlyTransactionManager
    private var contract: Movement?=null
    init {
        contractManager = ReadonlyTransactionManager(web3j,address)

        contract = Movement.load(address,web3j,contractManager,DefaultGasProvider())


    }





    fun getAllUsers(): List<User> {

        val userList= emptyList<User>().toMutableList()
        val usersCount= this.contract?.usersCount?.sendAsync()?.get()?.toInt()
        for (i in 0 until usersCount!!){
            val (userId,username,password,address,
                    isExist,routesStarted,routesFinished,routesCompleted)=
                checkNotNull(this.contract?.users(BigInteger(i.toString()))?.sendAsync()?.get())

            userList.add(i,User(userId.toString(),username,password,address,
                    routesStarted.toInt(),routesFinished.toInt(),routesCompleted.toInt()))
        }
        return userList
    }

    fun getAllRoutes():List<Route> {

        val routeList = emptyList<Route>().toMutableList()
        val routesCount= this.contract?.routesCount?.sendAsync()?.get()?.toInt()

        for(i in 0 until routesCount!!) {
            val (routeId,maker,taker,startLocation,endLocation,isStarted
                    ,isFinished,isCompleted,description) =
                checkNotNull(this.contract?.routes(BigInteger(i.toString()))?.sendAsync()?.get()
            )
            routeList.add(i,
                Route(routeId.toString(),maker,taker,startLocation,endLocation,isStarted,isFinished,isCompleted,description)
            )

        }
        return routeList
    }

    fun getAllNodes():List<Node>{
        val nodesList = emptyList<Node>().toMutableList()
        val nodes_count = this.contract?.nodesCount?.sendAsync()?.get()?.toInt()

        for(i in 0 until nodes_count!!) {
            val(nodeId,nodeName,ip,endopoint,oracle_address,routesChecked) = checkNotNull(
                this.contract?.nodes(BigInteger(i.toString()))?.sendAsync()?.get())
            nodesList.add(i,
                Node(nodeId.toString(),nodeName,ip,endopoint,oracle_address,routesChecked.toInt()))
        }
        return nodesList

    }

    companion object {
        const val TAG = "SmartContract"
    }


}