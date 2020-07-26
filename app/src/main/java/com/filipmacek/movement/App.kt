package com.filipmacek.movement

import android.app.Application
import androidx.room.Room
import com.filipmacek.movement.chains.ChainInfoProvider
import com.filipmacek.movement.data.AppDatabase
import com.filipmacek.movement.data.config.Settings
import com.filipmacek.movement.data.users.UsersRepository
import com.filipmacek.movement.utils.BigIntegerJSONAdapter
import com.filipmacek.movement.viewmodels.UserListViewModel
import com.squareup.moshi.Moshi
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

open class App: Application() {

    val appDatabase: AppDatabase by inject()
    val settings:Settings by inject()

    private val koinModules = module {
        single { Moshi.Builder().add(BigIntegerJSONAdapter()).build()  }
        single { ChainInfoProvider(get(),get(),get(),assets) }
        single { AppDatabase.getInstance(applicationContext) }
        single { get<AppDatabase>().userDao()}
        single { get<AppDatabase>().chainInfoDAO()}

        single {UsersRepository(get())}


        viewModel { UserListViewModel() }

    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(koinModules))
        }

    }





}