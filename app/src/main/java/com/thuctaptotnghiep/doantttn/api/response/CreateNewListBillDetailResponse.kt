package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataCreateNewListBillDetailResponse(
    @SerializedName("message")
    var message:String
)

data class CreateNewListBillDetailResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataCreateNewListBillDetailResponse,
    @SerializedName("flag")
    var flag:Boolean
)