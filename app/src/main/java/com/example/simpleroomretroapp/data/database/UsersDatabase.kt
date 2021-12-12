package com.example.simpleroomretroapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simpleroomretroapp.data.database.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun getUsersDao(): UserDao
}