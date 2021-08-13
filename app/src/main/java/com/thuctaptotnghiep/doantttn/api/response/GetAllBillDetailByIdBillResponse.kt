package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName


data class BillDetail(
    @SerializedName("Bill_idBill")
    var idBill:String,
    @SerializedName("PriceList_Shop_idShop")
    var idShop:String,
    @SerializedName("PriceList_Product_idProduct")
    var idProduct:String,
    @SerializedName("PriceDetail")
    var priceDetail:Double,
    @SerializedName("PriceOrigin")
    var priceOrigin:Double,
    @SerializedName("amount")
    var amount:Int,
    @SerializedName("nameProduct")
    var nameProduct:String,
    @SerializedName("nameShop")
    var nameShop:String,
    @SerializedName("image")
    var image:ImageDataFromMySQL
)

data class DataBillDetailByIdBill(
    @SerializedName("result")
    var result:List<BillDetail>
)

class GetAllBillDetailByIdBillResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataBillDetailByIdBill,
    @SerializedName("flag")
    var flag:Boolean
)