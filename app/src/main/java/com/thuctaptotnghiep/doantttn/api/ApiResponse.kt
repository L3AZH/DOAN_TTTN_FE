package com.thuctaptotnghiep.doantttn.api

import com.google.gson.Gson
import retrofit2.Response



sealed class ApiResponse<T> {
    companion object{
        inline fun <reified T> create(response: Response<T>):ApiResponse<T>{
            return if (response.isSuccessful){
                ApiResponseSuccessed(body = response.body()!!)
            }
            else{
                val gson = Gson()
                val responseFailed = gson.fromJson(
                    response.errorBody()!!.string(),
                    T::class.java
                )
                ApiResponseFailed(bodyError = responseFailed)
            }
        }
    }
}
data class ApiResponseSuccessed<T>(val body: T ):ApiResponse<T>()
data class ApiResponseFailed<T>(val bodyError: T):ApiResponse<T>()