package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.RequestIncome
import com.example.takasimura.model.ResponseIncome
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import retrofit2.Response

class IncomeRepository(private val context: Context) {

    private val apiService: ApiService

    init {
        val retrofitClient = RetrofitClient(context)
        apiService = retrofitClient.create(ApiService::class.java)
    }

    // Fungsi suspend untuk registrasi user
    suspend fun income (date:String, amount:Int, wallet:String, description:String, category:String): Response<ResponseIncome> {
        val request = RequestIncome(date,amount,wallet,description,category )
        return apiService.income(request)
    }
}
