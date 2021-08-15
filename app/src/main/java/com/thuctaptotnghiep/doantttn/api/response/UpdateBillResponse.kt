package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataUpdateBillResposne(
    @SerializedName("message")
    var message:String
)

data class UpdateBillResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataUpdateBillResposne,
    @SerializedName("flag")
    var flag:Boolean
)