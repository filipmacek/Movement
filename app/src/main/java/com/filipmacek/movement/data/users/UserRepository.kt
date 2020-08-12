package com.filipmacek.movement.data.users

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.filipmacek.movement.api.UserApi
import com.filipmacek.movement.blockchain.SmartContract
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.bouncycastle.LICENSE


class UserRepository(private val userDao: UserDao) {

    fun getUsers()=userDao.getAll()

    fun getUserByUsername(username:String)= userDao.getUserByUsername(username)

}

