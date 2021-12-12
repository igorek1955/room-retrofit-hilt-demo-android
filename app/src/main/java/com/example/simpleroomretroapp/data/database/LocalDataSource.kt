package com.example.simpleroomretroapp.data.database

import com.example.simpleroomretroapp.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val userDao: UserDao){
    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    suspend fun insertUserList(userList: List<UserEntity>) {
        userDao.insertUserList(userList)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

    suspend fun deleteUser(userEntity: UserEntity) {
        userDao.deleteUser(userEntity)
    }
}