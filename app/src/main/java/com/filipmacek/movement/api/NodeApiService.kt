package com.filipmacek.movement.api

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*

data class Ready(var status:String)

interface NodeApiService {

    @GET("ready")
    fun ready():Call<Ready>

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