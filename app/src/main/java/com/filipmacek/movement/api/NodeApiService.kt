package com.filipmacek.movement.api

import com.filipmacek.movement.data.location.Coordinate
import com.google.gson.GsonBuilder
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

data class Ready(var status:String)

interface NodeApiService {

    @GET("{data_endpoint}/ready")
    fun ready(@Path("data_endpoint") endpoint:String):Call<Ready>

    @POST("{data_endpoint}/route/{routeId}")
    fun postData(@Path("data_endpoint") endpoint: String, @Path("routeId") routeId:String,@Body coordinate: Coordinate):Flowable<Coordinate>

    @POST("{data_endpoint}/route/{routeId}/clear")
    fun clearAllData(@Path("data_endpoint") endpoint: String, @Path("routeId") routeId: String):Completable

    companion object {

        fun create(nodeIp:String):NodeApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .baseUrl("http://"+nodeIp+"/")
                .build()
            return retrofit.create(NodeApiService::class.java)
        }

    }
}