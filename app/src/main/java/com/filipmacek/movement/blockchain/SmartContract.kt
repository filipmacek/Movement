package com.filipmacek.movement.blockchain

import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.users.User
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import java.nio.charset.Charset
import com.filipmacek.movement.BuildConfig

class SmartContract(){
    private  var web3j:Web3j
    private val urlInfura= BuildConfig.INFURA_NODE_URL
    private val address=BuildConfig.CONTRACT_ADDRESS
    private val contractManager: ReadonlyTransactionManager
    private var contract: Movement_contract?=null
    init {
        web3j = Web3j.build(InfuraHttpService(urlInfura))
        println(web3j.ethBlockNumber())
        contractManager = ReadonlyTransactionManager(web3j,address)

        contract = Movement_contract.load(address,web3j,contractManager,DefaultGasProvider())
    }

    fun getAllUsers(): List<User> {

        val userList= emptyList<User>().toMutableList()
        val usersCount= this.contract?.usersCount?.sendAsync()?.get()?.toInt()
        for (i in 0 until usersCount!!){
            val (username,password,address,isExist)=
                checkNotNull(this.contract?.users(BigInteger(i.toString()))?.sendAsync()?.get())

            userList.add(i,User(username,password,address))
        }
        return userList
    }

    fun getAllRoutes():List<Route> {

        val routeList = emptyList<Route>().toMutableList()
        val routesCount= this.contract?.routesCount?.sendAsync()?.get()?.toInt()

        for(i in 0 until routesCount!!) {
            val (routeId,maker,taker,startLocation,endLocation,isStarted,isFinished) =
                checkNotNull(this.contract?.routes(BigInteger(i.toString()))?.sendAsync()?.get()
            )
            routeList.add(i,
                Route(routeId.toString(),maker,taker,startLocation,endLocation,isStarted,isFinished)
            )

        }
        return routeList
    }

    fun getAllNodes():List<Node>{
        val nodesList = emptyList<Node>().toMutableList()
        val nodes_count = this.contract?.nodesCount?.sendAsync()?.get()?.toInt()

        for(i in 0 until nodes_count!!) {
            val(nodeId,nodeName,ip,endopoint,oracle_address) = checkNotNull(
                this.contract?.nodes(BigInteger(i.toString()))?.sendAsync()?.get())
            nodesList.add(i,
                Node(nodeId.toString(),nodeName,ip,endopoint,oracle_address)
                )
        }
        return nodesList

    }


}