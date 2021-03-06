package com.filipmacek.movement.data.nodes

import android.util.Log
import androidx.annotation.RequiresPermission
import com.filipmacek.movement.api.NodeApiService
import com.filipmacek.movement.api.Ready
import com.filipmacek.movement.data.location.Coordinate
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.web3j.abi.datatypes.Bool
import retrofit2.Call
import kotlin.collections.ArrayList

class NodeRepository(private val nodeDao: NodeDao) {
    private val nodeServiceMap: MutableMap<String,NodeApiService> = mutableMapOf()

     val nodeStatus:ArrayList<BehaviorSubject<Boolean>> = arrayListOf(
             BehaviorSubject.createDefault(false),
             BehaviorSubject.createDefault(false)
     )


    fun getNodes() = nodeDao.getAll()

    fun getNodesSync()= nodeDao.getAllSync()

    fun getNodeById(nodeId:String) = nodeDao.getNodeById(nodeId)

    fun postDataToNode(node:Node,routeId:String,coordinate: Coordinate): Flowable<Coordinate> {
        return (nodeServiceMap[node.ip] ?: NodeApiService.create(node.ip).also {nodeServiceMap[node.ip] = it}).postData(node.dataEndpoint,routeId,coordinate)
    }

    fun checkIfNodeReady(nodeIp:String,dataEndpoint:String) = (nodeServiceMap[nodeIp] ?: NodeApiService.create(nodeIp)
                .also {
                    nodeServiceMap[nodeIp]=it
                }).ready(dataEndpoint)

    fun clearRouteData(nodeIp: String,dataEndpoint: String,routeId: String) = (nodeServiceMap[nodeIp] ?: NodeApiService.create(nodeIp)
            .also {
                nodeServiceMap[nodeIp]=it
            }).clearAllData(dataEndpoint,routeId)

}

