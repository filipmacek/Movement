package com.filipmacek.movement.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.filipmacek.movement.data.location.Coordinate
import com.filipmacek.movement.data.location.CoordinatesDao
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.nodes.NodeDao
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.routes.RouteDao
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserDao

@Database(entities = arrayOf(User::class,Route::class,Node::class,Coordinate::class),version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract fun userDao(): UserDao

    abstract fun routeDao():RouteDao

    abstract fun nodeDao():NodeDao

    abstract fun coordinatesDao():CoordinatesDao

}

