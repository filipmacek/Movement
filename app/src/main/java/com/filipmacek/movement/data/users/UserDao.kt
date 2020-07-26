package com.filipmacek.movement.data.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll():List<User>

    @Query("SELECT * FROM users WHERE username= :username")
    suspend fun getUserByUsername(username: String):User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: List<User>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

}