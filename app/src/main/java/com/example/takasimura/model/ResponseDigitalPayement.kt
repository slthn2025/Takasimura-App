package com.example.takasimura.model

import com.google.gson.annotations.SerializedName

data class ResponseDigitalPayement(

	@field:SerializedName("totalDigitalPaymentWallet")
	val totalDigitalPaymentWallet: Int? = null
)
