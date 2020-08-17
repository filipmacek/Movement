package com.filipmacek.movement

import android.app.Application
import androidx.room.Room
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.filipmacek.movement.api.UserApi
import com.filipmacek.movement.blockchain.SmartContract
import com.filipmacek.movement.blockchain.SmartContractAgent
import com.filipmacek.movement.data.AppDatabase
import com.filipmacek.movement.data.location.CoordinatesDao
import com.filipmacek.movement.data.location.LocationRepository
import com.filipmacek.movement.data.nodes.NodeDao
import com.filipmacek.movement.data.nodes.NodeRepository
import com.filipmacek.movement.data.routes.RouteDao
import com.filipmacek.movement.data.routes.RouteRepository
import com.filipmacek.movement.data.users.UserDao
import com.filipmacek.movement.data.users.UserRepository
import com.filipmacek.movement.viewmodels.*
import com.filipmacek.movement.workers.RPCRequestWorker
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    companion object {
        private var app: App? = null

        fun getApp():App? {
            return app
        }
    }

    val viewModelModule = module {
        viewModel { UserViewModel(get()) }
        viewModel { RouteViewModel(get()) }
        viewModel { NodeViewModel(get()) }
        viewModel { MainActivityViewModel() }
        viewModel { AccountViewModel() }
        viewModel {MovementViewModel(get(),get(),get(),get(),get()) }
    }



    val apiModule = module {
        fun providerUserApi(retrofit: Retrofit):UserApi {
            return retrofit.create(UserApi::class.java)
        }
        single { providerUserApi(get()) }

    }

    val netModule = module {
        fun provideCache(application: Application):Cache {
            val cacheSize= 10*1024*1024
            return Cache(application.cacheDir,cacheSize.toLong())
        }

        fun provideHttpClient(cache: Cache):OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .cache(cache)
            return okHttpClientBuilder.build()
        }

        fun provideGson():Gson {
            return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        }

        fun provideRetrofit(factory: Gson,client: OkHttpClient):Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.filipmacek.io/")
                .addConverterFactory(GsonConverterFactory.create(factory))
                .client(client)
                .build()
        }

        single { provideCache(androidApplication()) }
        single { provideHttpClient(get())}
        single { provideGson()}
        single { provideRetrofit(get(),get()) }
    }

    val databaseModule = module {
        fun provideDatabase(application: Application):AppDatabase {
            return Room.databaseBuilder(application,AppDatabase::class.java,"maindb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        fun provideUserDao(database: AppDatabase):UserDao{
            return database.userDao()
        }
        fun provideRouteDao(database: AppDatabase):RouteDao{
            return database.routeDao()
        }
        fun provideNodeDao(database: AppDatabase):NodeDao {
            return database.nodeDao()
        }
        fun provideLocationDao(database: AppDatabase):CoordinatesDao{
            return database.coordinatesDao()
        }


        single { provideDatabase((androidApplication()))}
        single { provideUserDao(get())}
        single { provideRouteDao(get())}
        single { provideNodeDao(get())}
        single { provideLocationDao(get()) }
    }

    val repositoryModule = module {
        fun provideUserRepository(dao:UserDao):UserRepository {
            return UserRepository(dao)
        }

        fun provideRouteRepository(dao:RouteDao):RouteRepository{
            return RouteRepository(dao)
        }
        fun provideNodeRepository(dao:NodeDao):NodeRepository{
            return NodeRepository(dao)
        }
        fun provideLocationRepository(dao:CoordinatesDao,repo:NodeRepository):LocationRepository{
            return LocationRepository(dao,repo)
        }

        single { provideUserRepository(get())}
        single { provideRouteRepository(get())}
        single { provideNodeRepository(get()) }
        single { provideLocationRepository(get(),get())}
    }

    val smartContractModule = module {
        fun provideWeb3():Web3j {
            return Web3j.build(InfuraHttpService(BuildConfig.INFURA_NODE_URL))
        }

        fun provideSmartContract(web3j: Web3j):SmartContract {
            return SmartContract(web3j)
        }
        fun provideSmartContractAgent(web3j: Web3j):SmartContractAgent{
            return SmartContractAgent(web3j)
        }
        single {provideSmartContract(get())}
        single {provideSmartContractAgent(get())}
        single {provideWeb3()}
    }






    override fun onCreate() {
        super.onCreate()
        app=this

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule,repositoryModule,netModule,apiModule,databaseModule,smartContractModule))
        }


        // START coroutines to build database
        val request_nodes = OneTimeWorkRequestBuilder<RPCRequestWorker>().setInputData(workDataOf("entity" to "nodes")).build()
        val request_users = OneTimeWorkRequestBuilder<RPCRequestWorker>().setInputData(workDataOf("entity" to "users")).build()
        val request_routes = OneTimeWorkRequestBuilder<RPCRequestWorker>().setInputData(workDataOf("entity" to "routes")).build()

        WorkManager.getInstance(applicationContext).enqueue(listOf(request_nodes,request_routes,request_users))
    }






}