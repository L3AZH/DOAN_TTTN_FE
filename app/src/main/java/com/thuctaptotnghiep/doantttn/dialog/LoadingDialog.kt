package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.LoadingDialogBinding

class LoadingDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            val binding: LoadingDialogBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.loading_dialog, null, false)
            builder.setView(binding.root)
            this.isCancelable = false
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty !!!")
    }

    fun cancelLoading(){
        if(dialog == null){
            Log.i("Error loading dialog", "cant get dialog to cancel ")
        }
        else{
            dialog?.cancel()
        }
    }
}