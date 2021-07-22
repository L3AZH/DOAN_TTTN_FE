package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class NewObjectRegisterResponse(
    @SerializedName("idAccount")
    var idAccount:String,
    @SerializedName("email")
    var email:String,
    @SerializedName("role")
    var role:String
)

data class DataResisterResponse(
    @SerializedName("message")
    var message:String,
    @SerializedName("newObejct")
    var newObject:NewObjectRegisterResponse
)

data class RegisterResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataResisterResponse,
    @SerializedName("flag")
    var flag:Boolean
) {
}