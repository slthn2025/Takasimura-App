package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.ResponseIncomeList
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class IncomeListRepository private constructor(context: Context) {

    private val apiService: ApiService = RetrofitClient(context).create(ApiService::class.java)

    // Fungsi untuk mengambil data cash
    suspend fun getIncomeList(): Response<List<ResponseIncomeList>> {
        return try {
            apiService.getIncomeList()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, null) // Kembalikan error jika terjadi masalah
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: IncomeListRepository? = null

        fun getInstance(context: Context): IncomeListRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: IncomeListRepository(context).also { INSTANCE = it }
            }
        }
    }
}