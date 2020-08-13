package com.filipmacek.movement.data.location

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import kotlin.math.abs
import kotlin.math.sqrt

val x = Coordinate("12:12:04 1.1.2019",45.813079, 15.997649,1)
val y = Coordinate("12:14:08 1.1.2019",45.811507, 15.998793,1)

data class TimeStamp(val s:String){
    var minutes:Int? = null
    var hours:Int? = null
    var seconds:Int? =null
    init {
        val str = s.substring(0,s.indexOf(" "))
        hours = str.substring(0,str.indexOf(":",0)).toInt()
        minutes = str.substring(str.indexOf(":",0)+1,str.indexOf(":",s.indexOf(":")+1)).toInt()
        seconds = str.takeLast(2).toInt()

    }

    // It will give time change in seconds
    fun subSeconds(tmp:TimeStamp):Int{
        return abs(tmp.hours!! - this!!.hours!!)*60*60+abs(tmp.minutes!! - this!!.minutes!!)*60+abs(tmp.seconds!!- this!!.seconds!!)
    }

    // It will give time change in hours
    fun subHours(tmp:TimeStamp):Double{
        val seconds = tmp.subSeconds(this)
        return (seconds.toDouble()/3600)
    }
}

@Entity(tableName = "currentCoordinates")
data class Coordinate(
        @PrimaryKey val timeStamp:String,
        val latitude:Double,
        val longitude:Double,
        val index:Int
){
    fun getDistance(tmp:Coordinate):Double{
        val location_A = Location("A")
        location_A.latitude = tmp.latitude
        location_A.longitude = tmp.longitude

        val location_B = Location("B")
        location_B.latitude = latitude
        location_B.longitude = longitude

        return location_A.distanceTo(location_B).toDouble()
    }


    fun getVelocityMs(tmp:Coordinate):Double{
        val t1=TimeStamp(tmp.timeStamp)
        val t2=TimeStamp(this.timeStamp)

        val delta_time= t2.subSeconds(t1)
        val delta_space:Double=tmp.getDistance(this)


        if(delta_time==0) {
            return 0.0
        } else {
            return (delta_space/delta_time)
        }

    }
    fun getVelocityKmh(tmp:Coordinate):Double{
        val t1=TimeStamp(tmp.timeStamp)
        val t2=TimeStamp(this.timeStamp)

        val delta_time= t2.subHours(t1)
        val delta_space:Double=tmp.getDistance(this)/1000

        if(delta_time == 0.0) {
            return 0.0
        } else {
            return (delta_space/delta_time)
        }




    }

    fun checkIfNearby(tmp:Coordinate,threshold:Int):Boolean{
        val distance = this.getDistance(tmp)
        return distance < threshold.toDouble()
    }

}