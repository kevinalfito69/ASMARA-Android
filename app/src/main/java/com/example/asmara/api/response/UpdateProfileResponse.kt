package com.example.asmara.api.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
data class UpdateProfileResponse(

	@field:SerializedName("email")
	val email: String,
	@field:SerializedName("username")
	val username: String,
	@field:SerializedName("message")
	val message: String
)
