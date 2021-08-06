package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.App
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainGuestViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    var listCategory: MutableLiveData<List<Category>> = MutableLiveData()
    var listProductByCategory: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        (application as App).getDataComponent().inject(this)
    }

    fun getAllCategory(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllCategory(token)
        if (response.isSuccessful) {
            listCategory.postValue(response.body()!!.data.result)
        }
    }

    fun getListProductByCategory(token: String, idCategory: String) =
        CoroutineScope(Dispatchers.Default).launch {
            val response = repository.getListProductByCategory(token, idCategory)
            if (response.isSuccessful) {
                listProductByCategory.postValue(response.body()!!.data.result)
            }
        }

}