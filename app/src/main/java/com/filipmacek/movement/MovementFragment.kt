package com.filipmacek.movement

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.*
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.filipmacek.movement.data.location.Coordinate
import com.filipmacek.movement.data.location.CoordinatesDao
import com.filipmacek.movement.data.location.Timer
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.databinding.MovementFragmentBinding
import com.filipmacek.movement.services.MovementLocationService
import com.filipmacek.movement.viewmodels.MovementViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt
import com.filipmacek.movement.R
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.workers.RouteFinishedWorker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.runBlocking


fun TextView.blink(
        times: Int = Animation.INFINITE,
        duration: Long = 50L,
        offset: Long = 20L,
        minAlpha: Float = 0.0f,
        maxAlpha: Float = 1.0f,
        repeatMode: Int = Animation.REVERSE
) {
    startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
        it.duration = duration
        it.startOffset = offset
        it.repeatMode = repeatMode
        it.repeatCount = times
    })
}

data class Destination(
        val latitude:Double,
        val longitude:Double
)
class MovementFragment :Fragment(),OnMapReadyCallback{

    private val compositeDisposable =CompositeDisposable()

    private lateinit var route:Route

    private var dataStatusPointTextViews:ArrayList<TextView> = arrayListOf()

    private var nodeStatusConnectionImageViews:ArrayList<ImageView> = arrayListOf()

    private  var timer:Timer = Timer()


    private val coordinatesDao:CoordinatesDao by inject()

    private lateinit var map:GoogleMap

    private lateinit var binding:MovementFragmentBinding

    private val viewModel: MovementViewModel by viewModel()


    private var  locationService: MovementLocationService? = null

    private lateinit var locationBroadcastReceiver: LocationBroadcastReceiver

    private var locationServiceBound=false

    var currentCoordinate:Coordinate = Coordinate("",0.0,0.0,1)



    // Monitors connection to the while-in-use service
    private val locationServiceConnection = object: ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i(TAG,"Location service connected!!")
            val binder = service as MovementLocationService.LocalBinder
            locationService = binder.service
            locationServiceBound=true

            // Start receiving location updates
            locationService?.subscribeToLocationUpdates()

        }
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG,"Location service disconnected!!")
            locationService = null
            locationServiceBound = false

        }
    }

    @SuppressLint("NewApi", "CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =MovementFragmentBinding.inflate(inflater,container,false).apply {   }

        // Clear local database
        viewModel.clearDatabase()



        // Init route in viewModel
        runBlocking {
            viewModel.initViewModel(arguments?.getString("routeId").toString(),arguments?.getString("username").toString())
        }

        // Load static data
        route = viewModel.routeById(arguments?.getString("routeId").toString())
        loadUi()

        // Subscribe UI ( dynamic data loading )
        subscribeUi()

        // Init receiver
        locationBroadcastReceiver = LocationBroadcastReceiver()

        // Bild RouteFinishedWorker
        // Send info to smart contract about user action --- 1
        val worker = OneTimeWorkRequestBuilder<RouteFinishedWorker>()
                .setInputData(workDataOf("username" to viewModel.user?.username,
                        "routeId" to viewModel.route?.routeId,
                        "nodeId" to viewModel.nodes!![0].nodeId,"userAction" to "1","dataPoints" to "0")).build()



        // Set event if user press BACK button
        // Which means he is canceling route and we have to report in to smart contract
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        val dialog= MaterialAlertDialogBuilder(context)
                .setTitle("You are leaving started route.Are you sure you want to cancel it.")
                .setNegativeButton("Yes leave") { dialog, which ->

                    // Cancel navigation service
                    val cancelLocationIntent = Intent(context,MovementLocationService::class.java)
                    cancelLocationIntent.putExtra(MovementLocationService.EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION,true)
                    val serviceCancelPendingIntent = PendingIntent.getService(context,0,cancelLocationIntent,PendingIntent.FLAG_UPDATE_CURRENT)
                    serviceCancelPendingIntent.send()


                    WorkManager.getInstance(this!!.context!!).enqueue(listOf(worker))



                    val view = layoutInflater.inflate(R.layout.route_end_info,null)
                    view.findViewById<ImageView>(R.id.routeFinishImage).setImageResource(R.drawable.route_rejected)
                    val dialog =MaterialAlertDialogBuilder(context)
                            .setView(view)
                            .show()
                    val timer =object: CountDownTimer(3000,1000){
                        override fun onFinish(){
                            onDestroy()
                            dialog.dismiss()
                            binding.root.findNavController().navigateUp()

                        }

                        override fun onTick(p0: Long) {

                        }
                    }
                    timer.start()

                }.setPositiveButton("Stay"){dialog,which ->
                    binding.root.requestFocus()

                }.setCancelable(false)
        binding.root.setOnKeyListener(View.OnKeyListener { view, keyCode, keyEvent ->
            if(keyCode== KeyEvent.KEYCODE_BACK){
                Log.i(TAG,"BACK KEY PRESSED")
                dialog.show()
                binding.root.clearFocus()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })




        val mapFragment = childFragmentManager.findFragmentById(R.id.movement_map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        return binding.root


    }
    fun createNodeRow(index:Int,node:Node):Unit{
        val nodeListLayout = binding.nodesList

        val node_row = LinearLayout(context)
        node_row.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        node_row.orientation = LinearLayout.HORIZONTAL

        // Index of node
        val indexTextView = TextView(context)
        val params_index = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params_index.setMargins(20,8,15,15)

        indexTextView.layoutParams=params_index
        indexTextView.text = index.toString()+"."
        indexTextView.textSize = 14.0F
        indexTextView.setTextColor(Color.BLACK)


        //Name of node
        val nameTextView=TextView(context)
        val width_name = resources.getDimension(R.dimen.node_name).roundToInt()
        val params_name=LinearLayout.LayoutParams(width_name,LinearLayout.LayoutParams.WRAP_CONTENT)
        params_name.setMargins(20,8,15,15)



        nameTextView.layoutParams = params_name
        nameTextView.text=node.nodeName.toString()
        nameTextView.textSize = 14.0F
        nameTextView.setTextColor(Color.BLACK)

        // Ip address
        val ipTextView=TextView(context)
        val width_ip = resources.getDimension(R.dimen.node_ip).roundToInt()
        val params_ip=LinearLayout.LayoutParams(width_ip,LinearLayout.LayoutParams.WRAP_CONTENT)
        params_ip.setMargins(20,8,15,15)

        ipTextView.layoutParams = params_ip
        ipTextView.text=node.ip
        ipTextView.textSize = 14.0F
        ipTextView.setTextColor(Color.BLACK)

        // Data points
        val dataPoints = TextView(context)
        val params_dataPoints = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params_dataPoints.setMargins(180,8,15,15)


        dataPoints.layoutParams = params_dataPoints
        dataPoints.text="0/0"
        dataPoints.textSize = 14.0F
        dataPoints.setTextColor(Color.BLACK)

        // Add to list of dataStatusPointsTextViews
        dataStatusPointTextViews.add(dataPoints)

        // Stupid ?!
        if(index ==1 ){dataPoints.id=R.id.node_DataPoints_1}
        else if( index ==2){dataPoints.id=R.id.node_DataPoints_2}
        else if(index==3){dataPoints.id = R.id.node_DataPoints_3}
        else if(index ==4) {dataPoints.id = R.id.node_DataPoints_4}

        // Connection image view
        val connectionImageView = ImageView(context)
        val dimen = resources.getDimension(R.dimen.node_connection).roundToInt()
        val params_connection = LinearLayout.LayoutParams(dimen,dimen)
        params_connection.setMargins(25,10,10,10)

        connectionImageView.layoutParams = params_connection
        connectionImageView.setImageResource(R.drawable.node_inactive)

        // Add to list of nodeStatusConnectionImageViews
        nodeStatusConnectionImageViews.add(connectionImageView)


        node_row.addView(indexTextView)
        node_row.addView(nameTextView)
        node_row.addView(ipTextView)
        node_row.addView(dataPoints)
        node_row.addView(connectionImageView)

        nodeListLayout.addView(node_row)
    }

    private fun loadUi(){

        // Route data
        binding.routeIdInfo.text =route?.routeId

        // Timer
        binding.dataTimer.text=timer.toString()

        // Route status
        binding.dataStatus.text = "Waiting"
        binding.dataStatus.setTextColor(Color.RED)

        // Nodes
        viewModel.nodes?.mapIndexed{ index, node->
            createNodeRow(index = index+1,node = node)
        }




    }
    @SuppressLint("CheckResult", "SetTextI18n")
    private fun subscribeUi(){
        // Timer
        val timer =viewModel.timerInterval.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { t ->
            if(viewModel.startLocationStatus.value!!){
                timer.tick()
                binding.dataTimer.text = timer.toString()
            }
        }.addTo(compositeDisposable)

        // Distance
        viewModel.distanceKm.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ distance ->
                    binding.dataDistanceKm.text=makeDistanceString(distance)+" km"},
                        {error->Log.e(TAG, error.toString())}).addTo(compositeDisposable)
        viewModel.distanceM.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ distance ->
                    binding.dataDistanceM.text=makeDistanceString(distance)+" m"},
                        {error ->Log.e(TAG,error.toString())}).addTo(compositeDisposable)


        // Speed
        viewModel.speedKmh.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { speedKmh ->
            binding.dataSpeedKmh.text = makeDistanceString(speedKmh)+" km/h"
        }.addTo(compositeDisposable)
        viewModel.speedMs.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { speedMs ->
            binding.dataSpeedMs.text = makeDistanceString(speedMs)+" m/s"
        }.addTo(compositeDisposable)

        // Start location status
        viewModel.startLocationStatus.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { status ->
            if(status) {
                binding.startLocationStatus.visibility=View.GONE
                binding.startLocationCompleted.visibility = View.VISIBLE
                binding.dataStatus.text="In progress"
                binding.dataStatus.setTextColor(Color.parseColor("#AA0D6EB8"))
            }
        }.addTo(compositeDisposable)

        //End location status
        viewModel.endLocationStatus.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { status ->
            if(status && viewModel.startLocationStatus.value!!) {
                binding.endLocationStatus.visibility=View.GONE
                binding.endLocationCompleted.visibility = View.VISIBLE
                binding.dataStatus.text = "Completed"
                binding.dataStatus.setTextColor(Color.parseColor("#AA088C15"))
                timer.dispose()

                // ============= Route finished  ==============
                // Send info to smart contract about user action --- 2
                val worker = OneTimeWorkRequestBuilder<RouteFinishedWorker>()
                        .setInputData(workDataOf("username" to viewModel.user?.username,
                                "routeId" to viewModel.route?.routeId,
                                "nodes" to viewModel.nodes!![0].nodeId,"userAction" to "2","dataPoints" to viewModel.nodeStatusData[0].value.toString())).build()

                WorkManager.getInstance(this!!.context!!).enqueue(listOf(worker))


                val dialog =MaterialAlertDialogBuilder(context)
                        .setView(layoutInflater.inflate(R.layout.route_end_info,null))
                        .show()
                val timer =object: CountDownTimer(3000,1000){
                    override fun onFinish(){
                        onDestroy()
                        dialog.dismiss()
                        binding.root.findNavController().navigateUp()

                    }

                    override fun onTick(p0: Long) {

                    }
                }
                timer.start()




            }
        }.addTo(compositeDisposable)
        viewModel.nodeStatusData.mapIndexed { index, temp ->
            temp.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe{count->
                var all_points:Int? = null
                if(viewModel.pointLast.value == null){all_points=0}else {all_points= viewModel.pointLast.value?.index }
                getViewByString("node_DataPoints_"+(index+1).toString())?.text = count.toString()+"/"+all_points.toString()

            }.addTo(compositeDisposable)
        }
        viewModel.nodeStatusConnection.mapIndexed { index, status->
            status.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe { status ->
                if(status){
                    nodeStatusConnectionImageViews[index].setImageResource(R.drawable.node_active)
                }else {
                    nodeStatusConnectionImageViews[index].setImageResource(R.drawable.node_inactive)
                }
            }.addTo(compositeDisposable)
        }



        // Current location
        viewModel.pointLast
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {coordinate ->
                            binding.currentLocation.blink(1)
                            binding.dataPoints.blink(1)
                            binding.currentLocation.text = makeCoordinateString(coordinate)
                            binding.dataPoints.text = coordinate.index.toString()
                            dataStatusPointTextViews.mapIndexed { index, textView ->
                                textView.text = viewModel.nodeStatusData[index].value.toString()+"/"+coordinate.index.toString()
                            }

                        },
                        {error -> Log.i(TAG,"Error "+error) }).addTo(compositeDisposable)

    }

    override fun onStart() {
        super.onStart()

        // Bind service
        val serviceIntent = Intent(context,MovementLocationService::class.java)
        activity?.bindService(serviceIntent,locationServiceConnection,Context.BIND_AUTO_CREATE)


    }



    override fun onResume() {
        Log.i(TAG,"ONRESUME")

        super.onResume()

        // Register receiver
        LocalBroadcastManager.getInstance(activity?.applicationContext!!).registerReceiver(
            locationBroadcastReceiver,
            IntentFilter(MovementLocationService.ACTION_LOCATION_BROADCAST)

        )

    }

    override fun onPause() {
        Log.i(TAG,"ONPAUSE")
        LocalBroadcastManager.getInstance(activity?.applicationContext!!).unregisterReceiver(
            locationBroadcastReceiver
        )
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG,"ONSTOP")
        if(locationServiceBound) {

            activity?.unbindService(locationServiceConnection)
            locationServiceBound=false
        }
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"ONDESTROY")
        // Cancel navigation service
        val cancelLocationIntent = Intent(context,MovementLocationService::class.java)
        cancelLocationIntent.putExtra(MovementLocationService.EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION,true)
        val serviceCancelPendingIntent = PendingIntent.getService(context,0,cancelLocationIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        serviceCancelPendingIntent.send()

        // Clear composite disposable
        compositeDisposable.clear()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        map= googleMap!!
        map.isMyLocationEnabled = true

        val startLoc = getLatLongFromString(route?.startLocation)
        val endLoc =getLatLongFromString(route?.endLocation)

        // Add circles around start and end location
        map.addCircle(
            CircleOptions().center(startLoc).radius(20.0).strokeColor(Color.CYAN).fillColor(Color.LTGRAY)
        )
        map.addCircle(
            CircleOptions().center(endLoc).radius(20.0).strokeColor(Color.CYAN).fillColor(Color.LTGRAY)
        )


        val markerStart = MarkerOptions().position(startLoc)
            .title("Start Location")
            .icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        val marketEnd =MarkerOptions().position(endLoc)
            .title("End location")
            .icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        map.addMarker(markerStart).showInfoWindow()
        map.addMarker(marketEnd)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLoc, 13F))

    }


    private inner class LocationBroadcastReceiver: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {
                val location = intent?.getParcelableExtra<Location>(MovementLocationService.EXTRA_LOCATION)
                if(location != null) {
//                    binding..text=location.latitude.toString()+","+location.longitude.toString()
//                    currentCoordinate = Coordinate("",location.latitude,location.longitude,1)


                }
        }

    }

    private fun getLatLongFromString(s:String?): LatLng {
        val coma_index = s?.indexOf(",")
        return LatLng(
                s?.substring(0, coma_index!!)?.toDouble()!!,
                s.substring(coma_index!! + 1).toDouble()
        )
    }

    private fun makeProperString(temp:String):String{
            return temp.substring(0,12)
        }
    private fun makeCoordinateString(coordinate:Coordinate):String{
        return coordinate.latitude.toString()+","+ coordinate.longitude.toString()
    }

    private fun getDestinationFromString(s:String?):Destination {
        val coma_index=s?.indexOf(",")
        return Destination(
                s?.substring(0, coma_index!!)?.toDouble()!!,
                s.substring(coma_index!! + 1).toDouble()
        )
    }

    private fun getViewByString(s:String): TextView? {
        return view?.findViewById<TextView>(resources.getIdentifier(s,"id",resources.getResourcePackageName(R.id.used_for_package_name_retrieval)))

    }

    companion object{
        const val TAG = "MovementFragment"
    }

    private fun makeDistanceString(tmp: Double):String {
        if(tmp.toString() == "0.0"){return "0.0"}
        else{
            return tmp.toString().substring(0,tmp.toString().indexOf(".")+3)
        }
    }

}