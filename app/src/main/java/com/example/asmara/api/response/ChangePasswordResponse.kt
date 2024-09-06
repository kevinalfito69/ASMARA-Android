package com.example.asmara.api.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("detail")
	val detail: String
)
