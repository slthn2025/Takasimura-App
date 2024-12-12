package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.ResponseExpanseList
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class ExpanseListRepository private constructor(context: Context) {

    private val apiService: ApiService = RetrofitClient(context).create(ApiService::class.java)

    // Fungsi untuk mengambil data cash
    suspend fun getExpanseList(): Response<List<ResponseExpanseList>> {
        return try {
            apiService.getExpanseList()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, null) // Kembalikan error jika terjadi masalah
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ExpanseListRepository? = null

        fun getInstance(context: Context): ExpanseListRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ExpanseListRepository(context).also { INSTANCE = it }
            }
        }
    }
}