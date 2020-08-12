package com.filipmacek.movement.data.users

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")
data class User (
    @PrimaryKey val username: String,
    val password: String,
    val address: String
)
