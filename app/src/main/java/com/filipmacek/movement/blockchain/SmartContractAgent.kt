package com.filipmacek.movement.blockchain

import android.util.Log
import com.filipmacek.movement.BuildConfig
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.routes.RouteRepository
import com.filipmacek.movement.data.users.User
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Uint
import org.web3j.abi.datatypes.Utf8String
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import kotlin.Exception


const val USER_CANCELED=1
const val USER_SUBMITED=1

class SmartContractAgent (web3j: Web3j):KoinComponent{
    private val routeRepository:RouteRepository by inject()

    private val address = BuildConfig.CONTRACT_ADDRESS
    private val agent_address = BuildConfig.AGENT_ADDRESS

    private val credentials:Credentials = Credentials.create(BuildConfig.AGENT_PRIVATE_KEY)

    // Build TransactionManager
    private val transactionManager:TransactionManager =RawTransactionManager(web3j,credentials)

    private val contract = Movement.load(
            address,web3j,transactionManager,DefaultGasProvider()
    )





    fun routeStarted(user: User, route: Route, nodeList:List<Node>,node1Status:Boolean,node2Status:Boolean){
        Log.i(TAG,"Smart contract agent reporting that route started")

        // Function
        val function= Function(
                "startRouteEvent",
                mutableListOf(
                        Uint(BigInteger.valueOf(route.routeId.toLong())),
                        Utf8String(user.username),
                        Bool(node1Status),
                        Bool(node2Status)),
                mutableListOf()
        )

        // Encode function values in transaction format
        val txData = FunctionEncoder.encode(function)

        try {
            //Send transaction
            transactionManager.sendTransaction(
                    DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, address, txData, BigInteger.ZERO
            ).run {
                Log.i(TAG, "Transaction hash:  " + this.transactionHash.toString())
            }
        }catch (e:Exception){
            Log.e(TAG, "Error sending startRouteEvent transaction: $e")
        }


        // Instead of using events, change route status in your local database
        routeRepository.updateStartRouteStatus(route.routeId)



    }


    fun routeFinished(username: String?, routeId: String?,
                      userAction:Int?, dataPoints:Int?,node1DataPoints: Int?,node2DataPoints:Int?)
    {

        Log.i(TAG,"Smart contract agent reporting that route finished")
        var action:Int? = null
        // User action
        if(userAction ==1){ action = USER_CANCELED}else{action= USER_SUBMITED}


        // Function
        val function = Function(
                "endRouteEvent",
                mutableListOf(
                        Uint(BigInteger.valueOf(routeId!!.toLong())),
                        Utf8String(username),
                        Uint(BigInteger.valueOf(action.toLong())),
                        Uint(BigInteger.valueOf(dataPoints!!.toLong())),
                        Uint(BigInteger.valueOf(node1DataPoints!!.toLong())),
                        Uint(BigInteger.valueOf(node2DataPoints!!.toLong()))
                ),
                mutableListOf()
        )

        // Encode function values in transaction format
        val txData = FunctionEncoder.encode(function)

        try {
            // Send transaction
            transactionManager.sendTransaction(
                    DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT,address,txData, BigInteger.ZERO
            ).run {
                Log.i(TAG,"Transaction hash for endRouteEvent  "+this.transactionHash.toString())
            }

        }catch (e:Exception) {
            Log.e(TAG,"Error sending endRouteEvent transaction $e")
        }


        // Insted of using events subscription,change route statuses( isFinished and isStarted)
        routeRepository.updateEndRouteStatus(routeId,userAction)


    }

    companion object {
        const val TAG = "SmartContractAgent"
    }


}