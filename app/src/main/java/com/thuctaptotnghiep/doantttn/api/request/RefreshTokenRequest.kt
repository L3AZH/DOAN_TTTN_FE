package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refreshToken")
    var refreshToken:String
)