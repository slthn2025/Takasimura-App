package com.example.takasimura.model

import com.google.gson.annotations.SerializedName

data class ResponseProfile(

	@field:SerializedName("profile")
	val profile: Profile? = null
)

data class Profile(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("profileImageUrl")
	val profileImageUrl: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
