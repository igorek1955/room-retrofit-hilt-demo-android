package com.example.simpleroomretroapp.data.network

import com.example.simpleroomretroapp.models.Result
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val restApi: UsersRestApi
){
    suspend fun getUsers(page: Int): Response<Result> {
        return restApi.getUsers(page)
    }
}