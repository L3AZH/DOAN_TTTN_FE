package com.thuctaptotnghiep.doantttn.ui.LoginRegister

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.App
import com.thuctaptotnghiep.doantttn.api.response.ErrorResponse
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginAndRegisterVieModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    var loginResult:MutableLiveData<Map<String,Any>> = MutableLiveData()
    var registerResult:MutableLiveData<Map<String,Any>> = MutableLiveData()


    init {
        (application as App).getRepositoryComponent().inject(this)
    }

    fun login(username:String, password:String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.login(username,password)
        var resultMap = mutableMapOf<String,Any>()
        if(response.isSuccessful){
            resultMap["role"] = response.body()!!.data.role
            resultMap["flag"] = response.body()!!.flag
            resultMap["token"] = response.body()!!.data.token
            resultMap["refreshToken"] = response.body()!!.data.refreshToken
            loginResult.postValue(resultMap)
        }
        else{
            val error = ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["message"] = error.data.message
            resultMap["flag"] = error.flag
            loginResult.postValue(resultMap)
        }
    }

    fun validationBeforeRegister(password: String,confirmPassword:String):Boolean{
        return (password == confirmPassword)
    }

    fun register(username: String,password: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.register(username,password,"guest")
        var resultMap = mutableMapOf<String,Any>()
        if(response.isSuccessful){
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] =response.body()!!.data.message
            registerResult.postValue(resultMap)
        }
        else{
            val error = ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
            registerResult.postValue(resultMap)
        }
    }


}