package com.filipmacek.movement.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.filipmacek.movement.blockchain.SmartContract
import com.filipmacek.movement.data.routes.RouteDao
import com.filipmacek.movement.data.users.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.java.KoinJavaComponent.inject

class UsersRPCRequestWorker (
    context: Context,
    parameters: WorkerParameters):CoroutineWorker(context,parameters),KoinComponent {

    val userDao:UserDao by inject()
    val contract:SmartContract by inject()
    val routeDao:RouteDao by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        try {
            Log.i(TAG,"Importing users and routes into database")
            val users= contract.getAllUsers()
            val routes = contract.getAllRoutes()
            Log.i(TAG,"Network RPC call finished")

            userDao.insert(users)
            routeDao.insert(routes)

            Result.success()

        }catch (ex:Exception) {
            Log.e(TAG,"Error inserting users in database")
            Result.failure()
        }
    }
    companion object{
        private const val TAG="UsersRPCRequestWorker"
    }

}