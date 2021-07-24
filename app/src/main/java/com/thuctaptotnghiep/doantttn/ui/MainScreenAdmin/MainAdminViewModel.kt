package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.App
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

class MainAdminViewModel(application: Application):AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    var listCategory:MutableLiveData<List<Category>> = MutableLiveData()

    init {
        (application as App).getRepositoryComponent().inject(this)
    }

    fun getAllCategory(token:String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllCategory(token)
        if(response.isSuccessful){
            listCategory.postValue(response.body()!!.data.result)
        }
    }
}