package com.example.takasimura.model

import com.google.gson.annotations.SerializedName

data class ResponseExpanse(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
