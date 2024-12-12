package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.ResponseCash
import com.example.takasimura.model.ResponseDigitalPayement
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class DigitalPayRepository private constructor(context: Context) {

    private val apiService: ApiService = RetrofitClient(context).create(ApiService::class.java)

    // Fungsi untuk mengambil data cash
    suspend fun getDigitalPay(): Response<ResponseDigitalPayement> {
        return try {
            apiService.getDigitalPayment()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, null) // Kembalikan error jika terjadi masalah
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DigitalPayRepository? = null

        fun getInstance(context: Context): DigitalPayRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DigitalPayRepository(context).also { INSTANCE = it }
            }
        }
    }
}
