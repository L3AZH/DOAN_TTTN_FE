package com.thuctaptotnghiep.doantttn.utils


import android.icu.text.DecimalFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class CurrencyConvert {
    companion object{
        val TAG:String ="CurrencyConvert"
        var decimalFormat: DecimalFormat? = null

        @JvmName("getMyDecimalFormat")
        fun getDecimalFormat():DecimalFormat{
            if(decimalFormat == null){
                decimalFormat = DecimalFormat("#.##")
            }
            return decimalFormat!!
        }
        fun convertCurrencyToString(price:Double):String{
            var result = 0.0
            if(price<999){
                result = price
                Log.e(TAG, "convertCurrencyToString: 1" )
                Log.e(TAG, "convertCurrencyToString: $price" )
                Log.e(TAG, "convertCurrencyToString: $result" )
                return result.toString()
            }
            else if(price>999 && price<999999){
                result = price/1000
                Log.e(TAG, "convertCurrencyToString: 2" )
                Log.e(TAG, "convertCurrencyToString: $price" )
                Log.e(TAG, "convertCurrencyToString: $result" )
                return String.format("%s K", getDecimalFormat().format(result))
            }
            else if(price>999999 && price<999999999){
                result = price/1000000
                Log.e(TAG, "convertCurrencyToString: 3" )
                Log.e(TAG, "convertCurrencyToString: $price" )
                Log.e(TAG, "convertCurrencyToString: $result" )
                return String.format("%s million", getDecimalFormat().format(result))
            }
            else if(price>999999999 && price<999999999999){
                result = price/1000000000
                Log.e(TAG, "convertCurrencyToString: 4" )
                Log.e(TAG, "convertCurrencyToString: $price" )
                Log.e(TAG, "convertCurrencyToString: $result" )
                return String.format("%s billion", getDecimalFormat().format(result))
            }
            else{
                return "price is to big cant convert"
            }
        }
    }
}