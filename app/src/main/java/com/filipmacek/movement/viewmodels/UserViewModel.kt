package com.filipmacek.movement.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.filipmacek.movement.blockchain.SmartContract
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserRepository

class UserViewModel(private val userRepository: UserRepository):ViewModel() {
    val users :LiveData<List<User>> = userRepository.getUsers()

    fun checkIfValid(username:String):User = userRepository.getUserByUsername(username)


}