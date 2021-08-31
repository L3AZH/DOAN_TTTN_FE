package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ChangePasswordDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePasswordDialog(
    val viewModelAdmin: MainAdminViewModel?,
    val viewModelGuest: MainGuestViewModel?
) : DialogFragment() {

    lateinit var binding: ChangePasswordDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.change_password_dialog,
                null,
                false
            )
            builder.setView(binding.root)
            this.isCancelable = false

            setOnClickCancelBtn()
            setOnClickSaveBtn()

            builder.create()
        } ?: throw IllegalStateException("Activity must not empty!!")
    }

    fun setOnClickSaveBtn() {
        binding.saveChangePasswordBtn.setOnClickListener {
            if (viewModelAdmin != null) {
                changePasswordAdmin(viewModelAdmin)
            }
            if (viewModelGuest != null) {
                changePasswordGuest(viewModelGuest)
            }
        }
    }

    fun changePasswordAdmin(adminViewModel: MainAdminViewModel) {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val token = prefs.getString("token", "null")
        val idAccount = prefs.getString("idAccount", "null")
        CoroutineScope(Dispatchers.Default).launch {
            if (adminViewModel.validationBeforeRegister(
                    binding.newPasswordEditText.text.toString(),
                    binding.confirmNewPasswordEditText.text.toString()
                )
            ) {
                val result = adminViewModel.changePassword(
                    token!!,
                    idAccount!!,
                    binding.oldPasswordEditText.text.toString(),
                    binding.confirmNewPasswordEditText.text.toString()
                ).await()
                if (result["flag"] as Boolean) {
                    val informSuccessDialog = InformDialog("success", result["message"].toString())
                    informSuccessDialog.show(
                        requireActivity().supportFragmentManager,
                        "success dialog"
                    )
                    this@ChangePasswordDialog.dialog?.cancel()
                } else {
                    val informErrorDialog =
                        InformDialog("fail", result["message"].toString())
                    informErrorDialog.show(requireActivity().supportFragmentManager, "error dialog")
                }
            } else {
                val informErrorDialog =
                    InformDialog("fail", "Confirm and New password doesn't match")
                informErrorDialog.show(requireActivity().supportFragmentManager, "error dialog")
            }
        }
    }

    fun changePasswordGuest(guestViewModel: MainGuestViewModel) {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val token = prefs.getString("token", "null")
        val idAccount = prefs.getString("idAccount", "null")
        CoroutineScope(Dispatchers.Default).launch {
            if (guestViewModel.validationBeforeRegister(
                    binding.newPasswordEditText.text.toString(),
                    binding.confirmNewPasswordEditText.text.toString()
                )
            ) {
                val result = guestViewModel.changePassword(
                    token!!,
                    idAccount!!,
                    binding.oldPasswordEditText.text.toString(),
                    binding.confirmNewPasswordEditText.text.toString()
                ).await()
                if (result["flag"] as Boolean) {
                    val informSuccessDialog = InformDialog("success", result["message"].toString())
                    informSuccessDialog.show(
                        requireActivity().supportFragmentManager,
                        "success dialog"
                    )
                    this@ChangePasswordDialog.dialog?.cancel()
                } else {
                    val informErrorDialog =
                        InformDialog("fail", result["message"].toString())
                    informErrorDialog.show(requireActivity().supportFragmentManager, "error dialog")
                }
            } else {
                val informErrorDialog =
                    InformDialog("fail", "Confirm and New password doesn't match")
                informErrorDialog.show(requireActivity().supportFragmentManager, "error dialog")
            }
        }
    }

    fun setOnClickCancelBtn() {
        binding.cancelChangePasswordBtn.setOnClickListener {
            this.dialog?.cancel()
        }
    }
}