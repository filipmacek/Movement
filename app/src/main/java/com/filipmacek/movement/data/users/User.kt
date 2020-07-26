package com.filipmacek.movement.data.users

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users",primaryKeys = ["username"])
data class User (
    val username: String,
    val password: String,
    val address: String
)
