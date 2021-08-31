package com.thuctaptotnghiep.doantttn.utils

class CurrencyConvert {
    companion object{
        fun convertCurrencyToString(price:Double):String{
            if(price<1000) return price.toString();
            else if(price>1000 && price<1000000) return "${price/1000} k"
            else if(price>1000000 && price<10000000) return "${price/1000000} tr"
            else{
                return "price is to big cant convert"
            }
        }
    }
}