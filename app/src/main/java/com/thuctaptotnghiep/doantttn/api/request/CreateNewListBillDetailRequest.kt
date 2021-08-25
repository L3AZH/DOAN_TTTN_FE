package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class DataCreateNewListBillDetailRequest(
    @SerializedName("idBill")
    var idBill:String,
    @SerializedName("idProduct")
    var idProduct:String,
    @SerializedName("idShop")
    var idShop:String,
    @SerializedName("amount")
    var amount:Int
)

data class CreateNewListBillDetailRequest(
    @SerializedName("listData")
    var listData:List<DataCreateNewListBillDetailRequest>
)