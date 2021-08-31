package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ShopAddDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class ShopAddDialog : DialogFragment() {

    lateinit var binding: ShopAddDialogBinding
    lateinit var viewModel: MainAdminViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.shop_add_dialog, null, false)
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setOnclickAddShopBtn()

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty")
    }

    fun setOnclickAddShopBtn() {
        binding.addShopDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.createNewShop(
                    token!!,
                    binding.nameShopTextInputEditText.text.toString(),
                    binding.phoneShopTextInputEditText.text.toString(),
                    binding.addressShopTextInputEditText.text.toString()
                ).await()
                if ((result["flag"] as Boolean)) {
                    val dialoginform = InformDialog("success", result["message"].toString())
                    dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                    dialog?.cancel()
                } else {
                    val dialoginform = InformDialog("fail", result["message"].toString())
                    dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                }
            }
        }
    }
}