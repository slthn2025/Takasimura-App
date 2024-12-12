package com.example.takasimura.repository

import android.content.Context
import com.example.takasimura.model.ResponseProfile
import com.example.takasimura.network.ApiService
import com.example.takasimura.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class EditProfileRepository(private val context: Context) {


    private val apiService: ApiService by lazy {
        RetrofitClient(context).create(ApiService::class.java)
    }

    suspend fun editProfile(
        username: RequestBody,
        email: RequestBody,
        password: RequestBody,
        profileImage: MultipartBody.Part
    ): Response<ResponseProfile> {
        return apiService.editProfile(username, email, password, profileImage)
    }
}
