package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PriceListFullInformation(
    @SerializedName("Shop_idShop")
    var idShop: String,
    @SerializedName("Product_idProduct")
    var idProduct:String,
    @SerializedName("name")
    var nameShop:String,
    @SerializedName("nameProduct")
    var nameProduct:String,
    @SerializedName("price")
    var price:Double,
    /// cai nay lay trong getALL price LIst
    @SerializedName("image")
    var image:ImageDataFromMySQL,
    @SerializedName("address")
    var addressShop:String,
    @SerializedName("phone")
    var phoneShop:String
):Serializable

data class DataListPrictListByProductResponse(
    @SerializedName("result")
    var result: List<PriceListFullInformation>
)

data class GetListPriceListByProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataListPrictListByProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)