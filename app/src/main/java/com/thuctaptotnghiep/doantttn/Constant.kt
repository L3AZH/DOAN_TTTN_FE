package com.thuctaptotnghiep.doantttn

import android.util.Base64


object Constant {
    val BASICAUTH_USER = "appdoantttn"
    val BASICAUTH_PASSWORD = "n17dccn004"
    val BASE_URL = "http://192.168.1.13:7000/api/"
    val SHARE_PREFERENCE_NAME = "com.thuctaptotnghiep.doantttn.app_config"

    fun getEncryptAccount():String{
        val account = "${BASICAUTH_USER}:${BASICAUTH_PASSWORD}"
        val data = account.toByteArray()
        val base64 = Base64.encodeToString(data,Base64.NO_WRAP)
        return "Basic $base64"
    }

}