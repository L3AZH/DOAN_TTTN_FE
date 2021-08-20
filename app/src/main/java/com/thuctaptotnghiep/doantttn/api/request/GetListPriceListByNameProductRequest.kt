package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class GetListPriceListByNameProductRequest(
    @SerializedName("nameProduct")
    var nameProduct:String
)