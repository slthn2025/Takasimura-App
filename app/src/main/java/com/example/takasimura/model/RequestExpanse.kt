package com.example.takasimura.model

import com.google.gson.annotations.SerializedName

data class RequestExpanse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("wallet")
	val wallet: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
