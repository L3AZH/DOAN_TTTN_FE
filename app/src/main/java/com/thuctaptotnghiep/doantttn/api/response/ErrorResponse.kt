package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class DataMessageResponse(
    @SerializedName("message")
    var message: String
)

data class ErrorResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataMessageResponse,
    @SerializedName("flag")
    var flag:Boolean
){
    companion object{
        fun convertErrorBodyToErrorResponseClass(errorBody: ResponseBody):ErrorResponse{
            val gson = Gson()
            return gson.fromJson(
                errorBody.string(),
                ErrorResponse::class.java
            )
        }
    }
}