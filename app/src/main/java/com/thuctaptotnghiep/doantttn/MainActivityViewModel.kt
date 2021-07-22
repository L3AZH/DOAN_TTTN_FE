package com.thuctaptotnghiep.doantttn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.thuctaptotnghiep.doantttn.api.ApiResponse
import com.thuctaptotnghiep.doantttn.api.ApiResponseFailed
import com.thuctaptotnghiep.doantttn.api.ApiResponseSuccessed
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
        when(val body = ApiResponse.create(response)){
            is ApiResponseSuccessed -> {
                return@async response.code() == 200
            }
            is ApiResponseFailed -> {
                return@async false
            }
        }
    }

}