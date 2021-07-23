package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataLoginResponse(
    @SerializedName("message")
    var emai:String,
    @SerializedName("token")
    var token:String,
    @SerializedName("refreshToken")
    var refreshToken:String,
    @SerializedName("role")
    var role:String
)

data class LoginResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataLoginResponse,
    @SerializedName("flag")
    var flag:Boolean
)