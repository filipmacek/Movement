package com.filipmacek.movement

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.navArgs
import com.filipmacek.movement.data.location.Coordinate
import com.filipmacek.movement.data.location.CoordinatesDao
import com.filipmacek.movement.data.location.LocationRepository
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
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.get

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDateTime
import kotlin.coroutines.coroutineContext

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

    private lateinit var route:Route

    private lateinit var startDestination:Destination
    private lateinit var endDestionation:Destination

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

        // Clear database
        viewModel.clearDatabase()

        // Load static data
        route = viewModel.routeById(arguments?.getString("routeId").toString())
        startDestination = getDestinationFromString(route.startLocation)
        endDestionation = getDestinationFromString(route.endLocation)
        loadUi()

        // Subscribe UI ( dynamic data loading )
        subscribeUi()

        // Init receiver
        locationBroadcastReceiver = LocationBroadcastReceiver()



        val mapFragment = childFragmentManager.findFragmentById(R.id.movement_map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        return binding.root


    }

    private fun loadUi(){

        // Route data
        binding.routeIdInfo.text =makeProperString(route?.routeId)

    }
    @SuppressLint("CheckResult")
    private fun subscribeUi(){
        // Current location
        viewModel.getLastCoordinates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {coordinate ->
                            binding.currentLocation.blink(1)
                            binding.dataPoints.blink(1)
                            binding.currentLocation.text = makeCoordinateString(coordinate)
                            binding.dataPoints.text = coordinate.index.toString()
                        },
                        {error -> Log.i(TAG,"Error "+error) })

    }

    override fun onStart() {
        super.onStart()

        // Bind service
        val serviceIntent = Intent(context,MovementLocationService::class.java)
        activity?.bindService(serviceIntent,locationServiceConnection,Context.BIND_AUTO_CREATE)


    }

    override fun onResume() {
        super.onResume()

        // Register receiver
        LocalBroadcastManager.getInstance(activity?.applicationContext!!).registerReceiver(
            locationBroadcastReceiver,
            IntentFilter(MovementLocationService.ACTION_LOCATION_BROADCAST)

        )
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(activity?.applicationContext!!).unregisterReceiver(
            locationBroadcastReceiver
        )
        super.onPause()
    }

    override fun onStop() {
        if(locationServiceBound) {
            activity?.unbindService(locationServiceConnection)
            locationServiceBound=false
        }
        super.onStop()
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

    companion object{
        const val TAG = "MovementFragment"
    }

}