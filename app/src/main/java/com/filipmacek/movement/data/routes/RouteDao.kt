package com.filipmacek.movement.data.routes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface RouteDao {

    @Query("SELECT * FROM routes")
    fun getAll():Flowable<List<Route>>

    @Query("SELECT * FROM routes WHERE routeId= :routeId")
    fun getRouteById(routeId: String):Route

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: List<Route>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: Route)

    @Query("UPDATE routes SET isStarted = 1 WHERE routeId = :routeId ")
    fun routeStarted(routeId: String)


    @Query("UPDATE routes SET isStarted = 0, isFinished = 0 WHERE routeId = :routeId ")
    fun routeReset(routeId: String?)

    @Query("UPDATE routes SET isFinished = 1 WHERE routeId = :routeId")
    fun routeFinished(routeId: String?)

}