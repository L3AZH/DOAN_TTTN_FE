package com.thuctaptotnghiep.doantttn.repository

import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.api.ApiInterface
import com.thuctaptotnghiep.doantttn.api.request.LoginRequest
import com.thuctaptotnghiep.doantttn.api.request.RefreshTokenRequest
import com.thuctaptotnghiep.doantttn.api.request.RegisterRequest
import javax.inject.Inject

class Repository @Inject constructor(private val api:ApiInterface){

    suspend fun login(email: String, password: String) =
        api.login(Constant.getEncryptAccount(), LoginRequest(email, password))

    suspend fun register(email: String, password: String, role: String) =
        api.register(
            Constant.getEncryptAccount(),
            RegisterRequest(email, password, role)
        )

    suspend fun refreshToken(refreshToken: String) =
        api.refreshToken(
            Constant.getEncryptAccount(),
            RefreshTokenRequest(refreshToken)
        )

    suspend fun checkTokenExpire() = api.checkTokenExpire()

    suspend fun getAllCategory(token:String) = api.getAllCategory(token)
}