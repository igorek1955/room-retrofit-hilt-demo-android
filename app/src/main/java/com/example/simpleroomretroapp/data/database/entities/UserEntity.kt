package com.example.simpleroomretroapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpleroomretroapp.utilities.Constants.USERS_TABLE

@Entity(tableName = USERS_TABLE)
class UserEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    val avatar: String,
    val email: String,
    val firstName: String,
    val lastName: String,
) {
    override fun toString(): String {
        return "UserEntity(id=$id, avatar='$avatar', email='$email', firstName='$firstName', lastName='$lastName')"
    }
}