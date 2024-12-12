package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.ResponseCash
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class CashRepository private constructor(context: Context) {

    private val apiService: ApiService = RetrofitClient(context).create(ApiService::class.java)

    // Fungsi untuk mengambil data cash
    suspend fun getCashData(): Response<ResponseCash> {
        return try {
            apiService.getCashData()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, null) // Kembalikan error jika terjadi masalah
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CashRepository? = null

        fun getInstance(context: Context): CashRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CashRepository(context).also { INSTANCE = it }
            }
        }
    }
}
