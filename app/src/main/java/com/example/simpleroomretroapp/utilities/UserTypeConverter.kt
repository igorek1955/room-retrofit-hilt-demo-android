package com.example.simpleroomretroapp.utilities

import com.example.simpleroomretroapp.data.database.entities.UserEntity
import com.example.simpleroomretroapp.models.UserData

class UserTypeConverter {
    companion object {
        fun userDataToUserEntity(userData: UserData): UserEntity {
            return UserEntity(
                userData.id,
                userData.avatar,
                userData.email,
                userData.firstName,
                userData.lastName
            )
        }

        fun userEntityToUserData(userEntity: UserEntity): UserData {
            return UserData(
                userEntity.avatar,
                userEntity.email,
                userEntity.firstName,
                userEntity.id,
                userEntity.lastName
            )
        }
    }
}