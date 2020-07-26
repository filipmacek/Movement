package com.filipmacek.movement.api


import com.filipmacek.movement.data.users.User
import retrofit2.Call
import retrofit2.http.GET


interface APIService {
    @GET("users")
    fun getUsers():Call<List<User>>
}