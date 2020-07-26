package com.filipmacek.movement.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.filipmacek.movement.data.AppDatabase
import com.filipmacek.movement.data.users.User
import kotlinx.coroutines.coroutineScope

class InitDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context,workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            val users = listOf<User>(
                User("fimacek","pass","addr1"),
                                                User("joka","pass2","addr2"))

            val database=AppDatabase.getInstance(applicationContext)

            database.userDao().insert(users)
            Result.success()
                    }catch (ex:Exception) {
            Log.e("Error seeding database", ex.toString())
            Result.failure()
        }
    }
}

