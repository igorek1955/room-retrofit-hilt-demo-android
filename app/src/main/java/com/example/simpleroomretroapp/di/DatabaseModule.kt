package com.example.simpleroomretroapp.di

import android.content.Context
import androidx.room.Room
import com.example.simpleroomretroapp.data.database.UsersDatabase
import com.example.simpleroomretroapp.utilities.Constants.USERS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        UsersDatabase::class.java,
        USERS_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: UsersDatabase) = database.getUsersDao()
}