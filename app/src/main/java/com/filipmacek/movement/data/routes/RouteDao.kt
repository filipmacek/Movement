package com.filipmacek.movement.data.routes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface RouteDao {

    @Query("SELECT * FROM routes")
    fun getAll():LiveData<List<Route>>

    @Query("SELECT * FROM routes WHERE routeId= :routeId")
    fun getRouteById(routeId: String):Route

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: List<Route>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: Route)

}