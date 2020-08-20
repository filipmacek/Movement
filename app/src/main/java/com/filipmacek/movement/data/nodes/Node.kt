package com.filipmacek.movement.data.nodes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodes")
data class Node (
    @PrimaryKey val nodeId:String,
    val nodeName:String,
    val ip:String,
    val dataEndpoint:String,
    val oracleContractAddress:String,
    val routesChecked:Int
)