package com.filipmacek.movement.data.chainInfo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChainInfoDAO {

    @Query("SELECT * FROM chains")
    suspend fun getAll():List<ChainInfo>

    @Query("SELECT * FROM chains WHERE chainId = :chainId")
    suspend fun getChainById(chainId: Int): ChainInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: ChainInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: List<ChainInfo>)
}