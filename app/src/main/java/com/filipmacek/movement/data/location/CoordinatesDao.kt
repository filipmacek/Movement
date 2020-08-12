package com.filipmacek.movement.data.location

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import jnr.ffi.annotations.In

@Dao
interface CoordinatesDao {

    @Query("SELECT * from currentCoordinates")
    fun getAll():LiveData<List<Coordinate>>

    @Query("SELECT * from currentCoordinates")
    fun getAllSync():List<Coordinate>

    @Query("SELECT * FROM currentCoordinates WHERE timeStamp= :timeStamp")
    fun getCoordinatesByTimestamp(timeStamp:String):Coordinate

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coordinates: Coordinate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coordinates: List<Coordinate>)

    @Query("DELETE FROM currentCoordinates")
    fun deleteAll()

    @Query("SELECT * from currentCoordinates ORDER BY `index` DESC LIMIT 1")
    fun getLast(): Observable<Coordinate>

    @Query("SELECT `index` FROM currentCoordinates ORDER BY `index` DESC LIMIT 1")
    fun getLastIndex():Observable<Int>
}