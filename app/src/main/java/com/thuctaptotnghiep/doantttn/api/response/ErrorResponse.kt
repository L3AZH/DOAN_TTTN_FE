package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("message")
    var message:String,
    @SerializedName("flag")
    var flag:Boolean
)