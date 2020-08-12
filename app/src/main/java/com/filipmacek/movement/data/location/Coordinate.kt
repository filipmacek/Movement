package com.filipmacek.movement.data.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currentCoordinates")
data class Coordinate(
        @PrimaryKey val timeStamp:String,
        val latitude:Double,
        val longitude:Double,
        val index:Int
)