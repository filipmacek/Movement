package com.filipmacek.movement.data.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import com.filipmacek.movement.data.nodes.NodeRepository
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class LocationRepository(private val coordinatesDao: CoordinatesDao,
                         private val nodeRepository: NodeRepository) {

    fun getLocations() = coordinatesDao.getAll()

    fun getLastIndex() :Int{
        return coordinatesDao.getAllSync().size
    }
    fun getLastCoordinate() = coordinatesDao.getLast()


    fun getLocationByTimeStamp(timeStamp: String) = coordinatesDao.getCoordinatesByTimestamp(timeStamp)

    fun insertLocation(location: Location){
        coordinatesDao.insert(Coordinate(createTimeStamp(),location.latitude,location.longitude,getLastIndex()+1))

    }

    fun clearAll() = coordinatesDao.deleteAll()

    @SuppressLint("NewApi")
    private fun createTimeStamp():String{
        val current = LocalDateTime.now(ZoneId.of("Europe/Zagreb"))
        val formater = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")
        val formated=current.format(formater)
        return formated
    }
}