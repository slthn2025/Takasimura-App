package com.example.takasimura.model

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
