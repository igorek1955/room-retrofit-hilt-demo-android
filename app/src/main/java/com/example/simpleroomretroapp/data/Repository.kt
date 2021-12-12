package com.example.simpleroomretroapp.data

import com.example.simpleroomretroapp.data.database.LocalDataSource
import com.example.simpleroomretroapp.data.network.RemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}