package com.filipmacek.movement.data.users

class UsersRepository private constructor(private val userDao: UserDao) {

    suspend fun getUsers() = userDao.getAll()

}

