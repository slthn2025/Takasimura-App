package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.RequestRegister
import com.example.takasimura.model.ResponseRegister
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class RegisterRepository(private val context: Context) {

    private val apiService: ApiService

    init {
        val retrofitClient = RetrofitClient(context)
        apiService = retrofitClient.create(ApiService::class.java)
    }

    // Fungsi suspend untuk registrasi user
    suspend fun registerUser(username: String, email: String, password: String): Response<ResponseRegister> {
        val request = RequestRegister(username, email, password)
        return apiService.register(request)
    }
}
