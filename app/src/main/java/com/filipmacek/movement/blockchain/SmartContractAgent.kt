package com.filipmacek.movement.blockchain

import android.util.Log
import com.filipmacek.movement.BuildConfig
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.users.User
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.gas.DefaultGasProvider


class SmartContractAgent (web3j: Web3j){
    private val urlInfura = BuildConfig.INFURA_NODE_URL
    private val address = BuildConfig.CONTRACT_ADDRESS
    private val credentials:Credentials = Credentials.create(BuildConfig.AGENT_PRIVATE_KEY)
    private val transactionManager:RawTransactionManager = RawTransactionManager(web3j,credentials)
    private val contract = Movement_contract.load(
            address,web3j,transactionManager,DefaultGasProvider()
    )


    companion object{
        const val TAG= "SmartContractAddress"
    }

    fun routeStarted(user: User, route: Route, nodeList:List<Node>){

    }
    fun routeFinished(user:User,route:Route,nodeList: List<Node>){

    }


}