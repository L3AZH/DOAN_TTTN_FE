package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.InformDialogFailBinding
import com.thuctaptotnghiep.doantttn.databinding.InformDialogSuccessBinding
import java.lang.IllegalStateException

class InformDialog(val type: String, val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            if (type == "success") {
                val binding: InformDialogSuccessBinding =
                    DataBindingUtil.inflate(inflater, R.layout.inform_dialog_success, null, false)
                binding.textInformSuccess.text = message
                builder.setView(binding.root)
            } else {
                val binding: InformDialogFailBinding =
                    DataBindingUtil.inflate(inflater, R.layout.inform_dialog_fail, null, false)
                binding.textInformFail.text = message
                builder.setView(binding.root)
            }
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty")
    }

}