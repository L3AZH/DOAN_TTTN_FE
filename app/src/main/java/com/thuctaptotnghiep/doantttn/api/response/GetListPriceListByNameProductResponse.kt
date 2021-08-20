package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataGetListPriceListByNameProduct(
    @SerializedName("result")
    var result:List<PriceListFullInformation>
)

data class GetListPriceListByNameProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataGetListPriceListByNameProduct,
    @SerializedName("flag")
    var flag:Boolean
)