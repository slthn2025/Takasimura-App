package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.RequestExpanse
import com.example.takasimura.model.ResponseExpanse
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class ExpanseRepository(private val context: Context) {

    private val apiService: ApiService

    init {
        val retrofitClient = RetrofitClient(context)
        apiService = retrofitClient.create(ApiService::class.java)
    }

    // Fungsi suspend untuk registrasi user
    suspend fun expanse (date:String, amount:Int, wallet:String, description:String, category:String): Response<ResponseExpanse> {
        val request = RequestExpanse(date,amount,wallet,description,category )
        return apiService.expanse(request)
    }
}
