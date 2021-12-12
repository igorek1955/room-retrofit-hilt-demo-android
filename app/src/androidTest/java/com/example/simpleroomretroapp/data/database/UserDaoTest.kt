package com.example.simpleroomretroapp.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.simpleroomretroapp.data.database.entities.UserEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var database: UsersDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UsersDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getUsersDao()
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun insertUser() {
        val userEntity = UserEntity(1, "path", "email@email.com", "jack", "oneil")
        val allUsers = arrayListOf<UserEntity>()
        testScope.launch {
            dao.insertUser(userEntity)
            dao.getAllUsers().collect {
                allUsers.addAll(it) }
        }
        assertThat(allUsers).isNotEmpty()
        assertThat(allUsers.stream().filter{user -> user.id == 1}.findAny().get()).isNotNull()
    }

    @Test
    fun deleteUser() {
        val userEntity = UserEntity(2, "path", "email@email.com", "jack", "oneil")
        val allUsers = arrayListOf<UserEntity>()
        testScope.launch {
            dao.insertUser(userEntity)
            dao.deleteUser(userEntity)
            dao.getAllUsers().collect {
                allUsers.addAll(it) }
        }
        assertThat(allUsers).isEmpty()
    }

    @Test
    fun insertUserList() {
        val userListToSave = arrayListOf(
            UserEntity(2, "path", "email@email.com", "jack", "oneil"),
            UserEntity(3, "path", "email@email.com", "jack", "oneil"),
            UserEntity(4, "path", "email@email.com", "jack", "oneil"),
            UserEntity(5, "path", "email@email.com", "jack", "oneil")
        )
        val allUsers = arrayListOf<UserEntity>()
        testScope.launch {
            dao.insertUserList(userListToSave)
            dao.getAllUsers().collect {
                allUsers.addAll(it) }
        }
        assertThat(allUsers).isNotEmpty()
        assertThat(allUsers.size == 4).isTrue()
    }

    @Test
    fun deleteAllUsers() {
        val userListToSave = arrayListOf(
            UserEntity(2, "path", "email@email.com", "jack", "oneil"),
            UserEntity(3, "path", "email@email.com", "jack", "oneil"),
            UserEntity(4, "path", "email@email.com", "jack", "oneil"),
            UserEntity(5, "path", "email@email.com", "jack", "oneil")
        )
        val allUsers = arrayListOf<UserEntity>()
        testScope.launch {
            dao.insertUserList(userListToSave)
            dao.deleteAllUsers()
            dao.getAllUsers().collect {
                allUsers.addAll(it) }
        }
        assertThat(allUsers).isEmpty()
    }
}