package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.IntroConfrimBillDialogBinding

class IntroductionForConfirmBillDialog : DialogFragment() {

    lateinit var binding: IntroConfrimBillDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.intro_confrim_bill_dialog,
                null,
                false
            )
            this.isCancelable = false
            builder.setView(binding.root)
            setOnOkBtnClick()
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty")
    }

    fun setOnOkBtnClick() {
        binding.understandBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            if (binding.rememberCheckBox.isChecked) {
                prefs.edit().putBoolean("dontShowIntroductionConfirmBill", true).apply()
            } else {
                prefs.edit().putBoolean("dontShowIntroductionConfirmBill", false).apply()
            }
            dialog?.cancel()
        }
    }
}