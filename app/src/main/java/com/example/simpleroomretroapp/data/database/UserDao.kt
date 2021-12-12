package com.example.simpleroomretroapp.data.database

import androidx.room.*
import com.example.simpleroomretroapp.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserList(userList: List<UserEntity>)

    @Query("DELETE FROM users_table")
    suspend fun deleteAllUsers()

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
}