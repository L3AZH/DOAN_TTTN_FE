package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataGetAllBillResponse(
    @SerializedName("result")
    var result:List<Bill>
)

data class GetAllBillResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataGetAllBillResponse,
    @SerializedName("flag")
    var flag:Boolean
)