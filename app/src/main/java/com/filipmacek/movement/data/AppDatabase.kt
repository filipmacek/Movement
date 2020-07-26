package com.filipmacek.movement.data

import android.content.Context
import android.nfc.Tag
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.filipmacek.movement.data.chainInfo.ChainInfo
import com.filipmacek.movement.data.chainInfo.ChainInfoDAO
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserDao
import com.filipmacek.movement.workers.InitDatabaseWorker

@Database(entities = arrayOf(User::class,ChainInfo::class),version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chainInfoDAO(): ChainInfoDAO

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "maindb")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        val request = OneTimeWorkRequestBuilder<InitDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                        Log.i("App Database", "Init database")

                    }
                }).build()

        }

    }
}

