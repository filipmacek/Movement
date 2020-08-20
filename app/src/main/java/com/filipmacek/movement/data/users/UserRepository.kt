package com.filipmacek.movement.data.users


class UserRepository(private val userDao: UserDao) {

    fun getUsers()=userDao.getAll()

    fun getUserByUsername(username:String)= userDao.getUserByUsername(username)

}

