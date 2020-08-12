package com.filipmacek.movement.data.routes

class RouteRepository(private val routeDao: RouteDao) {
    fun getRoutes()= routeDao.getAll()

    fun getRouteById(routeId: String):Route = routeDao.getRouteById(routeId)
}