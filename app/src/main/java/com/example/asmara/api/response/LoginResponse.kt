package com.example.asmara.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("token")
    val token: String
)

data class User(

    @field:SerializedName("nik")
    val nik: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)

data class LoginPost(
    @field:SerializedName("username")
    val username: String,
    @field:SerializedName("password")
    val password: String
)
