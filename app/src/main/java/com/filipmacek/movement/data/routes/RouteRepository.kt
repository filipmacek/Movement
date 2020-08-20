package com.filipmacek.movement.data.routes

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable

class RouteRepository(private val routeDao: RouteDao) {
    private val compositeDisposable:CompositeDisposable = CompositeDisposable()

    fun getRoutes()= routeDao.getAll()

    fun getRouteById(routeId: String):Route = routeDao.getRouteById(routeId)

    @SuppressLint("CheckResult")
    fun updateStartRouteStatus(routeId:String) = routeDao.routeStarted(routeId)

    fun updateEndRouteStatus(routeId: String?, userAction: Int?){
        if(userAction ==1){
            // 1. change status of isStarted  to false
            // 2. because user CANCEL route we set isFinished to false
            // so other user can accept it
            routeDao.routeReset(routeId)
        }else{
            routeDao.routeFinished(routeId)
        }
    }

    companion object {
        const val TAG = "RouteRepository"
    }

}