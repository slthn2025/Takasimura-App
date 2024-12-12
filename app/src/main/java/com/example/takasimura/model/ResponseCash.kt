package com.example.takasimura.model

import com.google.gson.annotations.SerializedName

data class ResponseCash(

	@field:SerializedName("totalCashWallet")
	val totalCashWallet: Int? = null
)
