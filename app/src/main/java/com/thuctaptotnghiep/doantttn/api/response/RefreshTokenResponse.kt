package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataRefreshTokenResponse(
    @SerializedName("token")
    var token:String
)

data class RefreshTokenResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataRefreshTokenResponse,
    @SerializedName("flag")
    var flag:Boolean
)