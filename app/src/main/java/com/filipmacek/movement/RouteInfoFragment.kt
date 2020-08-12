package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdateFactory.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RouteInfoFragment :Fragment(),OnMapReadyCallback{

    private lateinit var map:GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View = inflater.inflate(R.layout.route_info,container,false)

       // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)


        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!
        val startLocation = arguments?.getString("startLocation")
        val endLocation= arguments?.getString("endLocation")

        val startLocationLatLong = getLatLongFromString(startLocation)
        val endLocationLatLong = getLatLongFromString(endLocation)

        val markerStart=MarkerOptions().position(startLocationLatLong)
            .title("Start Location").
            icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        val marketEnd =MarkerOptions().position(endLocationLatLong)
            .title("End location")
            .icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))


        map.addMarker(markerStart).showInfoWindow()
        map.addMarker(marketEnd)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocationLatLong, 13F))


//        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        map.moveCamera(newLatLng(sydney))

    }

    private fun getLatLongFromString(s:String?):LatLng {
        val coma_index=s?.indexOf(",")
        return LatLng(
            s?.substring(0, coma_index!!)?.toDouble()!!,
            s.substring(coma_index!! + 1).toDouble()
        )
    }
}
