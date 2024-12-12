package com.example.takasimura.network

import android.content.Context
import com.example.takasimura.BuildConfig
import com.example.takasimura.data.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(private val context: Context) {

    private val BASE_URL = BuildConfig.BASE_URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Interceptor untuk menambahkan header Authorization
    private fun createAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val dataStoreManager = DataStoreManager.getInstance(context)

            // Ambil token secara sinkron menggunakan runBlocking
            val token = runBlocking {
                dataStoreManager.getToken().firstOrNull() // Mengambil token, null jika tidak ada
            }

            val requestBuilder = chain.request().newBuilder()

            // Tambahkan header Authorization jika token tersedia
            if (token != null) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            chain.proceed(requestBuilder.build())
        }
    }

    // Membuat OkHttpClient dengan Interceptor
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Interceptor untuk logging
            .addInterceptor(createAuthInterceptor()) // Interceptor untuk Authorization
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Membuat Retrofit instance
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Fungsi untuk membuat service API berdasarkan interface service
    fun <T> create(service: Class<T>): T {
        return createRetrofit().create(service)
    }
}
