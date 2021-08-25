package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataGetListDetailShopProductByNameProduct(
    @SerializedName("result")
    var result:List<DetailShopProductFullInformation>
)

data class GetListDetailShopProductByNameProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataGetListDetailShopProductByNameProduct,
    @SerializedName("flag")
    var flag:Boolean
)