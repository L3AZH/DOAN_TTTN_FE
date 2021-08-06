package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class GetListProductByCategoryResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAllProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)