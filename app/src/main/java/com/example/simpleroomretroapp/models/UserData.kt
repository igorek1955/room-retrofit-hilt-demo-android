package com.example.simpleroomretroapp.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserData(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String
): Parcelable {
    override fun toString(): String {
        return "UserData(avatar='$avatar', email='$email', firstName='$firstName', id=$id, lastName='$lastName')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserData

        if (avatar != other.avatar) return false
        if (email != other.email) return false
        if (firstName != other.firstName) return false
        if (id != other.id) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatar.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + id
        result = 31 * result + lastName.hashCode()
        return result
    }

}