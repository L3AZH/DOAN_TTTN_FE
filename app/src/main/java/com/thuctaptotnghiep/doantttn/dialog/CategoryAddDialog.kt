package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.request.AddCategoryRequest
import com.thuctaptotnghiep.doantttn.databinding.CategoryAddDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class CategoryAddDialog():DialogFragment() {

    lateinit var binding:CategoryAddDialogBinding
    lateinit var viewModel:MainAdminViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(inflater, R.layout.category_add_dialog,null,false)
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setOnclickAddCategoryBtn()

            builder.setView(binding.root)
            builder.create()
        }?: throw IllegalStateException("Activity must not empty")
    }


    fun setOnclickAddCategoryBtn(){
        binding.addCategoryBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(Constant.SHARE_PREFERENCE_NAME,Context.MODE_PRIVATE)
            val token = prefs.getString("token","null")
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.createNewCategory(token!!,binding.nameCategoryInputTextEdit.text.toString()).await()
                if((result["flag"] as Boolean)){
                    val dialoginform = InformDialog("success",result["message"].toString())
                    dialoginform.show(requireActivity().supportFragmentManager,"dialog inform")
                    dialog?.cancel()
                }
                else{
                    val dialoginform = InformDialog("fail",result["message"].toString())
                    dialoginform.show(requireActivity().supportFragmentManager,"dialog inform")
                    dialog?.cancel()
                }
            }
        }
    }

}