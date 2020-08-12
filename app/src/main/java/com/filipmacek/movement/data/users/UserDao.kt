package com.filipmacek.movement.data.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE username= :username")
    fun getUserByUsername(username: String):User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: List<User>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

}