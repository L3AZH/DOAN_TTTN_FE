package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.App
import com.thuctaptotnghiep.doantttn.api.request.AddCategoryRequest
import com.thuctaptotnghiep.doantttn.api.request.UpdateCategoryRequest
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.api.response.ErrorResponse
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

class MainAdminViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    var listCategory: MutableLiveData<List<Category>> = MutableLiveData()
    var listProduct: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        (application as App).getRepositoryComponent().inject(this)
    }

    fun getAllCategory(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllCategory(token)
        if (response.isSuccessful) {
            listCategory.postValue(response.body()!!.data.result)
        }
    }

    fun createNewCategory(token: String, nameCategory: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.createNewCategory(token, AddCategoryRequest(nameCategory))
            var resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            getAllCategory(token)
            resultMap
        }

    fun deleteCategory(token: String, idCategory: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.deleteCategory(token, idCategory)
            var resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            getAllCategory(token)
            resultMap
        }

    fun updateCategory(
        token: String,
        idCategory: String,
        newCategoryname: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response =
            repository.updateCategory(token, idCategory, UpdateCategoryRequest(newCategoryname))
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllCategory(token)
        resultMap
    }

    fun getAllProduct(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllProduct(token)
        if (response.isSuccessful) {
            listProduct.postValue((response.body()!!.data.result))
        }
    }
}