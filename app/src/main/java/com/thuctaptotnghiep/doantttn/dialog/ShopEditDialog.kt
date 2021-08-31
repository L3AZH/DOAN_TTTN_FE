package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Shop
import com.thuctaptotnghiep.doantttn.databinding.ShopEditDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopEditDialog(var shop: Shop) : DialogFragment() {

    lateinit var binding: ShopEditDialogBinding
    lateinit var viewModel: MainAdminViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.shop_edit_dialog, null, false)
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setUpBinding()
            setOnClickDeleteBtn()
            setOnClickUpdateBtn()
            setOnclickCancelBtn()

            builder.setView(binding.root)
            builder.create()

        } ?: throw IllegalStateException("Acitivity must not empty")
    }

    fun setUpBinding() {
        binding.idShopForEditDialogTextView.text = shop.idShop
        binding.nameShopIntputTextEdit.setText(shop.name)
        binding.phoneShopIntputTextEdit.setText(shop.phone)
        binding.addressShopIntputTextEdit.setText(shop.address)
    }

    fun setOnClickDeleteBtn() {
        binding.deleteShopEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")!!
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.deleteShop(token, shop.idShop).await()
                if ((result["flag"] as Boolean)) {
                    val dialogInform = InformDialog("success", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                    dialog?.cancel()
                } else {
                    val dialogInform = InformDialog("fail", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                }
            }
        }
    }

    fun setOnClickUpdateBtn() {
        binding.saveShopEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")!!
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.updateShop(
                    token,
                    shop.idShop,
                    binding.nameShopIntputTextEdit.text.toString(),
                    binding.phoneShopIntputTextEdit.text.toString(),
                    binding.addressShopIntputTextEdit.text.toString()
                ).await()
                if ((result["flag"] as Boolean)) {
                    val dialogInform = InformDialog("success", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                    dialog?.cancel()
                } else {
                    val dialogInform = InformDialog("fail", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                }
            }
        }
    }

    fun setOnclickCancelBtn(){
        binding.cancelShopEditDialogBtn.setOnClickListener {
            dialog?.cancel()
        }
    }
}