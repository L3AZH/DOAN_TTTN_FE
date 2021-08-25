package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String,
    @SerializedName("role")
    var role:String,
    @SerializedName("phone")
    var phone:String,
    @SerializedName("address")
    var address:String
)