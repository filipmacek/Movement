package com.filipmacek.movement.data.nodes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NodeDao {
    @Query("SELECT * FROM nodes")
    fun getAll(): LiveData<List<Node>>

    @Query("SELECT * FROM nodes WHERE nodeId= :nodeId")
    fun getNodeById(nodeId: String): Node

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: List<Node>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: Node)

}