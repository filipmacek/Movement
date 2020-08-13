package com.filipmacek.movement.data.location
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.min

fun Int.length() = when(this) {
    0->1
    else -> log10(abs(toDouble())).toInt() + 1
}

data class Timer(
        var hours:Int =0,
        var minutes:Int = 0,
        var seconds:Int=0) {
    override fun toString(): String {
        // Hours
        var hours_string=""
        if(hours.length()==1){ hours_string = "0"+hours.toString() }else{ hours_string=hours.toString() }
        // Minutes
        var minutes_string=""
        if(minutes.length()==1){ minutes_string = "0"+minutes.toString() }else{ minutes_string=minutes.toString() }
        // Seconds
        var seconds_string=""
        if(seconds.length()==1){ seconds_string = "0"+seconds.toString() }else{ seconds_string=seconds.toString() }
        return hours_string+":"+minutes_string+":"+seconds_string
    }

    fun tick(){
        seconds++
        if(seconds == 60){
            seconds=0
            minutes=minutes+1
        }
        if(minutes == 60){
            minutes=0
            hours=hours+1
        }
        if(hours==24){
            seconds=0
            minutes=0
            hours=0
        }
    }
}