package com.filipmacek.movement.data.nodes

import android.util.Log
import androidx.annotation.RequiresPermission
import com.filipmacek.movement.api.NodeApiService
import com.filipmacek.movement.api.Ready
import io.reactivex.Observable
import java.util.*
import io.reactivex.schedulers.Schedulers

class NodeRepository(private val nodeDao: NodeDao) {
    private val nodeServiceMap: MutableMap<String,NodeApiService> = mutableMapOf()

    fun getNodes() = nodeDao.getAll()

    fun getNodeById(nodeId:String) = nodeDao.getNodeById(nodeId)

    fun checkIfNodeReady(nodeIp:String) = (nodeServiceMap[nodeIp] ?: NodeApiService.create(nodeIp)
                .also {
                    nodeServiceMap[nodeIp]=it
                }).ready()

    }

