package com.example.simpleroomretroapp.data.network

import androidx.test.filters.SmallTest
import com.example.simpleroomretroapp.models.UserData
import com.example.simpleroomretroapp.utilities.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SmallTest
class RemoteDataSourceTest {
    lateinit var restApi: UsersRestApi

    @Before
    fun setup() {
        restApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsersRestApi::class.java)
    }

    @Test
    fun getUsers() {
        val users = arrayListOf<UserData>()
        runBlocking {
            users.addAll(restApi.getUsers(2).body()!!.data)
        }
        assertThat(users).isNotEmpty()
    }
}