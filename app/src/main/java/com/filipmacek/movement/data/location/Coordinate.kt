package com.filipmacek.movement.data.location

import android.location.Location
import android.util.Log
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
        var secondsDiff = 0
        var minutesMinus = 0

        var minutesDiff = 0
        var hoursMinus = 0

        var hoursDiff=0

        // Seconds
        if(this.seconds!! < tmp.seconds!!){
            secondsDiff = (60 + this.seconds!! - tmp.seconds!!)
            minutesMinus=1
        }else{
            secondsDiff = this.seconds!! - tmp.seconds!!

        }

        // Minutes
        if(this.minutes!! < tmp.minutes!!){
            minutesDiff = (60 + this.minutes!! - tmp.minutes!!)-minutesMinus
            hoursMinus=1
        }else {

            minutesDiff = ( this.minutes!! - tmp.minutes!!)-minutesMinus
        }

        hoursDiff = this.hours!! - tmp.hours!!-hoursMinus
        return hoursDiff*60*60+
                minutesDiff*60+
                secondsDiff
    }

    // It will give time change in hours
    fun subHours(tmp:TimeStamp):Double{
        val seconds = this.subSeconds(tmp)
        return (seconds.toDouble()/3600)
    }
}

@Entity(tableName = "currentCoordinates")
data class Coordinate(
        @PrimaryKey val timestamp:String,
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
        val t1=TimeStamp(tmp.timestamp)
        val t2=TimeStamp(this.timestamp)
        val delta_time= t2.subSeconds(t1)
        val delta_space:Double=tmp.getDistance(this)

        if(delta_time==0) {
            return 0.0
        } else {
            return (delta_space/delta_time)
        }

    }
    fun getVelocityKmh(tmp:Coordinate):Double{
        val t1=TimeStamp(tmp.timestamp)
        val t2=TimeStamp(this.timestamp)

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