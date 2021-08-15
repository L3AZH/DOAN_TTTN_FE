package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataCreateNewBill(
    @SerializedName("message")
    var message:String,
    @SerializedName("idBill")
    var idBill:String
)

data class CreateNewBillResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataCreateNewBill,
    @SerializedName("flag")
    var flag:Boolean
)