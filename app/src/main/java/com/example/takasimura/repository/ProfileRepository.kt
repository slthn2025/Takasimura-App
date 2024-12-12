package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.ResponseProfile
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class ProfileRepository private constructor(context: Context) {

    private val apiService: ApiService = RetrofitClient(context).create(ApiService::class.java)

    // Fungsi untuk mengambil profil
    suspend fun getProfile(): Response<ResponseProfile> {
        return try {
            apiService.getProfile()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.error(500, null) // Kembalikan error jika terjadi masalah
        }
    }

    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance(context: Context): ProfileRepository {
            return instance ?: synchronized(this) {
                instance ?: ProfileRepository(context).also { instance = it }
            }
        }
    }
}
