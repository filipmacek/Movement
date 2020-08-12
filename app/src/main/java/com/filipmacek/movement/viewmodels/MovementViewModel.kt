package com.filipmacek.movement.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.filipmacek.movement.data.location.LocationRepository
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.routes.RouteRepository
import com.google.android.gms.maps.model.LatLng
import io.reactivex.subjects.BehaviorSubject

class MovementViewModel(private val routeRepository: RouteRepository,private val locationRepository: LocationRepository) :ViewModel() {




    fun routeById(routeId:String):Route = routeRepository.getRouteById(routeId)

    fun clearDatabase() = locationRepository.clearAll()

    fun getLastCoordinates() =locationRepository.getLastCoordinate()

    fun getDataPoints() =locationRepository.getLastIndex()

}