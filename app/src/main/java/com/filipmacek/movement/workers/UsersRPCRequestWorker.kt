package com.filipmacek.movement.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRPCRequest (
    context: Context,
    parameters: WorkerParameters):CoroutineWorker(context,parameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        
    }

}