package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Bill
import com.thuctaptotnghiep.doantttn.databinding.ConfirmBillAdminDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfirmBillDialog(val bill: Bill) : DialogFragment() {

    lateinit var binding: ConfirmBillAdminDialogBinding
    lateinit var viewModel: MainAdminViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.confirm_bill_admin_dialog,
                null,
                false
            )
            viewModel = (activity as MainAdminActivity).viewModel

            setUpBinding()
            setOnClickConfirmBillOkBtn()
            setOnClickConfirmCancelBtn()

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty !!")
    }

    fun setUpBinding() {
        if(bill.status == "Pending"){
            binding.confirmBillTextView.text = "Verify this bill \n ID: ${bill.idBill}"
            binding.okConfirmBillBtn.visibility = View.VISIBLE
        }
        else{
            binding.confirmBillTextView.text = "This Bill was verified !!"
            binding.okConfirmBillBtn.visibility = View.GONE
        }
    }

    fun setOnClickConfirmBillOkBtn() {
        binding.okConfirmBillBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val prefs = requireActivity().getSharedPreferences(
                    Constant.SHARE_PREFERENCE_NAME,
                    Context.MODE_PRIVATE
                )
                val token = prefs.getString("token", "null")
                val resulMap = viewModel.confirmBill(token!!, bill.idBill).await()
                if (resulMap["flag"] as Boolean) {
                    val successDialog = InformDialog("success", resulMap["message"].toString())
                    successDialog.show(
                        requireActivity().supportFragmentManager,
                        "success dialog inform"
                    )
                    dialog?.cancel()
                } else {
                    val failDialog = InformDialog("fail", resulMap["message"].toString())
                    failDialog.show(
                        requireActivity().supportFragmentManager,
                        "error dialog inform"
                    )
                    dialog?.cancel()
                }
            }
        }
    }

    fun setOnClickConfirmCancelBtn() {
        binding.cancelConfirmBillBtn.setOnClickListener {
            dialog?.cancel()
        }
    }
}