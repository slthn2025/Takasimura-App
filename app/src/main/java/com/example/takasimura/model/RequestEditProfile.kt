package com.example.takasimura.model

import com.google.gson.annotations.SerializedName

data class RequestEditProfile(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("profileImage")
	val profileImage: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
