package com.filipmacek.movement.blockchain

import android.util.Log
import com.filipmacek.movement.BuildConfig
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.users.User
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Uint
import org.web3j.abi.datatypes.Utf8String
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import org.web3j.tx.response.TransactionReceiptProcessor
import java.lang.Exception
import java.math.BigInteger


class SmartContractAgent (web3j: Web3j){
    private val address = BuildConfig.CONTRACT_ADDRESS
    private val agent_address = BuildConfig.AGENT_ADDRESS

    private val credentials:Credentials = Credentials.create(BuildConfig.AGENT_PRIVATE_KEY)

    // Build TransactionManager
    private val transactionManager:TransactionManager =RawTransactionManager(web3j,credentials)

    private val contract = Movement.load(
            address,web3j,transactionManager,DefaultGasProvider()
    )




    fun routeStarted(user: User, route: Route, nodeList:List<Node>){
        Log.i(TAG,"Smart contract agent reporting that route started")

        // Function
        val function= Function(
                "startRouteEvent",
                mutableListOf(
                        Uint(BigInteger.valueOf(route.routeId.toLong())),
                        Utf8String(user.username),Uint(BigInteger.valueOf(nodeList[0].nodeId.toLong()))),
                mutableListOf()
        )

        // Encode function values in transaction format data format
        val txData = FunctionEncoder.encode(function)

        try {
            //Send transaction
            transactionManager.sendTransaction(
                    DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, address, txData, BigInteger.ZERO
            ).run {
                Log.i(TAG, "Transaction hash:  " + this.transactionHash.toString())
            }
        }catch (e:Exception){
            Log.e(TAG, "Error sending transaction: $e")
        }


    }


    fun routeFinished(user:User,route:Route,nodeList: List<Node>){

    }

    companion object {
        const val TAG = "SmartContractAgent"
    }


}