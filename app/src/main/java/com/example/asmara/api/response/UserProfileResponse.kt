package com.example.asmara.api.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
