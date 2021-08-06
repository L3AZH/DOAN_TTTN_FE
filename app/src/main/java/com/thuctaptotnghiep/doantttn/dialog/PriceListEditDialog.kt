package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.ProductArrayAdapter
import com.thuctaptotnghiep.doantttn.adapter.ShopArrayAdapter
import com.thuctaptotnghiep.doantttn.api.response.PriceList
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.api.response.Shop
import com.thuctaptotnghiep.doantttn.databinding.PriceListEditDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PriceListEditDialog(val priceList: PriceList) : DialogFragment() {

    lateinit var binding: PriceListEditDialogBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var shopArrayAdapter: ShopArrayAdapter
    lateinit var productArrayAdapter: ProductArrayAdapter
    lateinit var selectedShop: Shop
    lateinit var selectedProduct: Product

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.price_list_edit_dialog,
                null,
                false
            )
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setUpBinding()
            setUpShopArrayAdapter()
            setUpProductArrayAdapter()
            setOnClickDeleteBtn()
            setOnClickUpdateBtn()

            builder.setView(binding.root)
            builder.create()

        } ?: throw  IllegalStateException("Activity must not empty")
    }

    fun setUpBinding() {
        binding.priceEditTextInputEditText.setText(priceList.price.toString())
        binding.imageProductOfShopEdit.setImageBitmap(
            BitmapFactory.decodeByteArray(
                priceList.image.data,
                0,
                priceList.image.data.size
            )
        )
    }

    fun setUpShopArrayAdapter() {
        shopArrayAdapter = ShopArrayAdapter(requireContext(), viewModel.listShop.value!!)
        (binding.shopEditSpinnervalue as AutoCompleteTextView).setAdapter(shopArrayAdapter)
        (binding.shopEditSpinnervalue as AutoCompleteTextView).onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedShop = shopArrayAdapter.getItem(position)!!
                }
            }
        selectedShop = shopArrayAdapter.getItemId(priceList.shopIdShop)!!
        binding.shopEditSpinnervalue.setText(selectedShop.name)
    }

    fun setUpProductArrayAdapter() {
        productArrayAdapter = ProductArrayAdapter(requireContext(), viewModel.listProduct.value!!)
        (binding.productEditSpinnerValue as AutoCompleteTextView).setAdapter(productArrayAdapter)
        (binding.productEditSpinnerValue as AutoCompleteTextView).onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedProduct = productArrayAdapter.getItem(position)!!
                }
            }
        selectedProduct = productArrayAdapter.getItemId(priceList.productIdProduct)!!
        binding.productEditSpinnerValue.setText(selectedProduct.name)
    }

    fun setOnClickDeleteBtn() {
        binding.deletePriceListEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")!!
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.deletePriceListObject(
                    token,
                    priceList.shopIdShop,
                    priceList.productIdProduct
                ).await()
                if ((result["flag"] as Boolean)) {
                    val dialogInform = InformDialog("success", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                    dialog?.cancel()
                } else {
                    val dialogInform = InformDialog("fail", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                    dialog?.cancel()
                }
            }
        }
    }

    fun setOnClickUpdateBtn(){
        binding.savePriceListEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")
            if (binding.imageProductOfShopEdit.drawable == null) {
                val dialoginform = InformDialog("fail", "Please choose image before input")
                dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                dialog?.cancel()
            }
            else if(!checkPricevalid()){
                val dialoginform = InformDialog("fail", "Please enter price")
                dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                dialog?.cancel()
            }
            else {
                CoroutineScope(Dispatchers.Default).launch {
                    val result = viewModel.updatePriceListObject(
                        token!!,
                        selectedShop.idShop,
                        selectedProduct.idProduct,
                        binding.priceEditTextInputEditText.text.toString().toDouble(),
                        (binding.imageProductOfShopEdit.drawable as BitmapDrawable).bitmap
                    ).await()
                    if ((result["flag"] as Boolean)) {
                        val dialoginform = InformDialog("success", result["message"].toString())
                        dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                        dialog?.cancel()
                    } else {
                        val dialoginform = InformDialog("fail", result["message"].toString())
                        dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                        dialog?.cancel()
                    }
                }
            }
        }
    }

    fun checkPricevalid():Boolean{
        try{
            binding.priceEditTextInputEditText.text.toString().toDouble()
            return true
        }
        catch (e:Exception){
            return false
        }
    }
}