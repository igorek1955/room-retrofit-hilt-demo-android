package com.example.simpleroomretroapp.models


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("data")
    val `data`: List<UserData>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)