package com.thuctaptotnghiep.doantttn.api

import com.thuctaptotnghiep.doantttn.api.request.LoginRequest
import com.thuctaptotnghiep.doantttn.api.request.RefreshTokenRequest
import com.thuctaptotnghiep.doantttn.api.request.RegisterRequest
import com.thuctaptotnghiep.doantttn.api.response.GetAllCategoryResponse
import com.thuctaptotnghiep.doantttn.api.response.LoginResponse
import com.thuctaptotnghiep.doantttn.api.response.RefreshTokenResponse
import com.thuctaptotnghiep.doantttn.api.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {


    @POST("auth/login")
    suspend fun login(
        @Header("Authorization") encryptAccount: String,
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(
        @Header("Authorization") encryptAccount: String,
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Header("Authorization") encryptAccount: String,
        @Body request: RefreshTokenRequest
    ): Response<RefreshTokenResponse>

    @GET("auth/check-token-expire")
    suspend fun checkTokenExpire():Response<Any>


    @GET("category/get-all-category")
    suspend fun getAllCategory(
        @Header("Authorization") token:String
    ):Response<GetAllCategoryResponse>


}