package com.filipmacek.movement.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.routes.RouteRepository
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserRepository

class RouteViewModel(private val routeRepository: RouteRepository): ViewModel() {
    val routes = routeRepository.getRoutes()





}