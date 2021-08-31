package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.databinding.CategoryEditDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryEditDialog(val category: Category) : DialogFragment() {

    lateinit var binding: CategoryEditDialogBinding
    lateinit var viewModel: MainAdminViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(inflater, R.layout.category_edit_dialog, null, false)
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setUpBinding()
            setOnlickDeleteBtn()
            setOnClickSaveBtn()
            setOnclickCancelBtn()

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty")
    }

    fun setUpBinding() {
        binding.idCategoryForEditDialogTextView.text = category.idCategory
        binding.nameCategoryEditInputTextEdit.setText(category.name)
    }

    fun setOnlickDeleteBtn() {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val token = prefs.getString("token", "null")!!
        binding.deleteCategoryBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.deleteCategory(token, category.idCategory).await()
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

    fun setOnClickSaveBtn() {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val token = prefs.getString("token", "null")!!
        binding.updateCategoryBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.updateCategory(
                    token,
                    category.idCategory,
                    binding.nameCategoryEditInputTextEdit.text.toString()
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
        binding.cancelEditCategoryBtn.setOnClickListener {
            dialog?.cancel()
        }
    }
}