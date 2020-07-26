package com.filipmacek.movement.data.chainInfo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
//
//data class NativeCurrency(
//    val symbol: String,
//    val name:String = symbol,
//    val decimals: Int = 18
//)

@Entity(tableName = "chains",primaryKeys = ["chainId"])
data class ChainInfo(
    val name:String,
    val chainId: Int,
    val networkId: Int,
    val shortName: String,
    val rpc: String

)
