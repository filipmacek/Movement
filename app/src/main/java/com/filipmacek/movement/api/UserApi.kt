package com.filipmacek.movement.api


import com.filipmacek.movement.data.users.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET


interface UserApi {
    @GET("users")
    suspend fun getUsersAsync(): Deferred<List<User>>
}