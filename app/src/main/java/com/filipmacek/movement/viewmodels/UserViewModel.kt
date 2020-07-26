package com.filipmacek.movement.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserRepository

class UserListViewModel(usersRepository: UserRepository):ViewModel(){
    private val users: MutableLiveData<List<User>> = MutableLiveData().also{
        loadUsers()

    }

    fun getData():LiveData<List<User>> {
        return users
    }

    private fun loadUsers() {nd

    }
}