package com.thuctaptotnghiep.doantttn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.api.response.ErrorResponse
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class MainActivityViewModel(application:Application):AndroidViewModel(application) {
    @Inject
    lateinit var repository: Repository



    init {
       (application as App).getRepositoryComponent().inject(this)
    }

    fun checkTokenNotExpire():Deferred<Boolean> = CoroutineScope(Dispatchers.Default).async {
        val response = repository.checkTokenExpire()
        if(response.isSuccessful){
            return@async response.code() == 200
        }
        else{
            return@async false
        }
    }

    fun getNewToken(token:String):Deferred<Map<String,Any>> = CoroutineScope(Dispatchers.Default).async {
        val response = repository.refreshToken(token)
        var resultMap:MutableMap<String,Any> = mutableMapOf()
        if(response.isSuccessful){
            resultMap["token"] = response.body()!!.data.token
            resultMap["flag"] = response.body()!!.flag
        }else{
            val error = ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["message"] = error.data.message
            resultMap["flag"] = error.flag
        }
        return@async resultMap
    }

}