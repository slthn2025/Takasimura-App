package com.example.takasimura.network


import com.example.takasimura.model.RequestExpanse
import com.example.takasimura.model.RequestIncome
import com.example.takasimura.model.RequestLogin
import com.example.takasimura.model.RequestRegister
import com.example.takasimura.model.ResponseCash
import com.example.takasimura.model.ResponseDigitalPayement
import com.example.takasimura.model.ResponseExpanse
import com.example.takasimura.model.ResponseExpanseList
import com.example.takasimura.model.ResponseIncome
import com.example.takasimura.model.ResponseIncomeList
import com.example.takasimura.model.ResponseLogin
import com.example.takasimura.model.ResponseProfile
import com.example.takasimura.model.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService {

//   Auth Endpoint
    @POST("/api/auth/signup")
    suspend fun register(@Body request: RequestRegister): Response<ResponseRegister>

    @POST("/api/auth/login")
    suspend fun login(@Body request: RequestLogin): Response<ResponseLogin>

//    Progfile Endpoint
    @GET("/api/profile/getprofile")
    suspend fun getProfile(): Response<ResponseProfile>

    @Multipart
    @PUT("/api/profile/editprofile")
    suspend fun editProfile(
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part profileImage: MultipartBody.Part
    ): Response<ResponseProfile>

    //Income Endpoint
    @GET("/api/wallet/cash")
    suspend fun getCashData(): Response<ResponseCash>

    @GET("/api/wallet/digital-payment")
    suspend fun getDigitalPayment(): Response<ResponseDigitalPayement>

    @POST("/api/income/income")
    suspend fun income(@Body request: RequestIncome): Response<ResponseIncome>

    @POST("/api/expanse/expanse")
    suspend fun expanse(@Body request: RequestExpanse): Response<ResponseExpanse>
    @GET("/api/income/income")
    suspend fun getIncomeList(): Response<List<ResponseIncomeList>>
    @GET("/api/expanse/expanse")
    suspend fun getExpanseList(): Response<List<ResponseExpanseList>>


}