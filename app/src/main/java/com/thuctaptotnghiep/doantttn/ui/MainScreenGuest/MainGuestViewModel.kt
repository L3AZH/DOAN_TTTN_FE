package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.App
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.api.response.PriceListFullInformation
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.db.model.Cart
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.*
import okhttp3.internal.wait
import javax.inject.Inject

class MainGuestViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    var listCategory: MutableLiveData<List<Category>> = MutableLiveData()
    var listListPriceListByProduct: MutableLiveData<List<PriceListFullInformation>> =
        MutableLiveData()
    var listCart: MutableLiveData<List<Cart>> = MutableLiveData()

    init {
        (application as App).getDataComponent().inject(this)
    }

    fun getAllCategory(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllCategory(token)
        if (response.isSuccessful) {
            listCategory.postValue(response.body()!!.data.result)
        }
    }

    fun getListProductByCategory(token: String, idCategory: String): Deferred<List<Product>?> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.getListProductByCategory(token, idCategory)
            if (response.isSuccessful) {
                response.body()!!.data.result
            } else {
                null
            }
        }

    fun getListPriceListByProduct(token: String, idProduct: String) =
        CoroutineScope(Dispatchers.Default).launch {
            val response = repository.getListPriceListByIdProduct(token, idProduct)
            if (response.isSuccessful) {
                listListPriceListByProduct.postValue(response.body()!!.data.result)
            }
        }

    fun clearListPriceListByProduct() {
        listListPriceListByProduct.postValue(null)
    }

    fun addToCart(
        priceListFullInformation: PriceListFullInformation,
        amount: Int,
        email: String
    ): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            var resultMap: MutableMap<String, Any> = mutableMapOf()
            val newCart = Cart(
                null,
                email,
                priceListFullInformation.idShop,
                priceListFullInformation.nameShop,
                priceListFullInformation.idProduct,
                priceListFullInformation.nameProduct,
                priceListFullInformation.price,
                amount,
                priceListFullInformation.image.data
            )
            try {
                var cartExist = repository.checkCartExistInDb(
                    priceListFullInformation.idShop,
                    priceListFullInformation.idProduct,
                    email
                )
                if (cartExist == null) {
                    repository.addToCart(newCart)
                } else {
                    val newAmount = cartExist.amount + amount
                    if(newAmount > 10){
                        resultMap["flag"] = false
                        resultMap["message"] = "Amount of product cant be greater than 10"
                        return@async resultMap
                    }
                    else{
                        cartExist.amount = newAmount
                    }
                    repository.updateCart(cartExist)
                }
                resultMap["flag"] = true
                resultMap["message"] = "Add to cart Successfully"
                resultMap
            } catch (ex: Exception) {
                ex.printStackTrace()
                resultMap["flag"] = true
                resultMap["message"] = "Add to cart Fail"
                resultMap
            }
        }

    fun getListCart(email: String) = CoroutineScope(Dispatchers.Default).launch {
        listCart.postValue(repository.getAllCart(email))
    }

    fun deleteCart(cart: Cart, email: String) = CoroutineScope(Dispatchers.Default).launch {
        repository.deleteCart(cart)
        getListCart(email)
    }

    fun updateCartWhenMinus(cart: Cart, email: String) =
        CoroutineScope(Dispatchers.Default).launch {
            if (cart.amount > 0) {
                cart.amount = cart.amount - 1
                if (cart.amount == 0) {
                    repository.deleteCart(cart)
                } else {
                    repository.updateCart(cart)
                }
                getListCart(email)
            }
        }

    fun updateCartWhenPlus(cart: Cart, email: String) =
        CoroutineScope(Dispatchers.Default).launch {
            if (cart.amount < 10) {
                cart.amount = cart.amount + 1
                if (cart.amount <= 10) {
                    repository.updateCart(cart)
                    getListCart(email)
                }
            }
        }
}