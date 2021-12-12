package com.example.simpleroomretroapp.data.network

import com.example.simpleroomretroapp.models.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersRestApi {
    @GET("/api/users")
    suspend fun getUsers(@Query("page") page: Int): Response<Result>
}