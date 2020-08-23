package com.filipmacek.movement.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.filipmacek.movement.blockchain.SmartContractAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject


class RouteFinishedWorker (
        context: Context,
        parameters: WorkerParameters): CoroutineWorker(context,parameters), KoinComponent {

  private val smartContractAgent:SmartContractAgent by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        try {
            smartContractAgent.routeFinished(
                    inputData.getString("username"),
                    inputData.getString("routeId"),
                    inputData.getString("userAction")?.toInt(),
                    inputData.getString("dataPoints")?.toInt(),
                    inputData.getString("node1DataPoints")?.toInt(),
                    inputData.getString("node2DataPoints")?.toInt()
            )

            Result.success()

        }catch (ex:Exception) {
            Log.e(TAG,"Error calling endRouteEvent on smart contract")
            Result.failure()
        }
    }
    companion object{
        private const val TAG="RouteFinishedWorker"
    }

}