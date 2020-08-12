package com.filipmacek.movement.data.routes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes")
data class Route (
    @PrimaryKey val routeId: String,
    val maker:String,
    val taker:String,
    val startLocation:String,
    val endLocation:String,
    val isStarted: Boolean,
    val isFinished:Boolean
)
