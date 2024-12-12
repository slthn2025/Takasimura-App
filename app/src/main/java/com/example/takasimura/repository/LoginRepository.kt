package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.RequestLogin
import com.example.takasimura.network.ApiService
import com.example.takasimura.model.RequestRegister
import com.example.takasimura.model.ResponseLogin
import com.example.takasimura.model.ResponseRegister
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class LoginRepository(private val context: Context) {

    private val apiService: ApiService

    init {
        val retrofitClient = RetrofitClient(context)
        apiService = retrofitClient.create(ApiService::class.java)
    }

    // Fungsi suspend untuk registrasi user
    suspend fun loginUser(email: String, password: String): Response<ResponseLogin> {
        val request = RequestLogin(email, password)
        return apiService.login(request)
    }
}
