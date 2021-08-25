package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class ImageDataFromMySQL(
    @SerializedName("type")
    var type:String,
    @SerializedName("data")
    var data:ByteArray
)

data class DetailShopProduct(
    @SerializedName("ShopIdShop")
    var shopIdShop:String,
    @SerializedName("ProductIdProduct")
    var productIdProduct:String,
    @SerializedName("price")
    var price:Double,
    @SerializedName("image")
    var image:ImageDataFromMySQL
)

data class DataAllDetailShopProductResponse(
    @SerializedName("result")
    var result:List<DetailShopProduct>
)

data class GetAllDetailShopProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAllDetailShopProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)