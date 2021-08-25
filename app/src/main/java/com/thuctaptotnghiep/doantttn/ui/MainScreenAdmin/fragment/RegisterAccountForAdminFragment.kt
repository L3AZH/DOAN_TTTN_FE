package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.FragmentRegisterAccountForAdminBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterAccountForAdminFragment : Fragment() {

    lateinit var binding: FragmentRegisterAccountForAdminBinding
    lateinit var viewModel: MainAdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_register_account_for_admin,
            container,
            false
        )
        viewModel = (activity as MainAdminActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickCreateAccountBtn()
    }

    fun setOnClickCreateAccountBtn() {
        binding.registerAdminBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                if (viewModel.validationBeforeRegister(
                        binding.passwordRegisterAdminEditText.text.toString(),
                        binding.confirmPasswordAdminEditText.text.toString()
                    )
                ) {
                    val resultMap = viewModel.registerAdminAccount(
                        binding.emailRegisterAdminEditText.text.toString(),
                        binding.confirmPasswordAdminEditText.text.toString(),
                        binding.phoneAdminEditText.text.toString(),
                        binding.addressAdminEditText.text.toString()
                    ).await()
                    if (resultMap["flag"] as Boolean) {
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.passwordRegisterAdminEditText.setText("")
                            binding.confirmPasswordAdminEditText.setText("")
                        }
                        Snackbar.make(
                            binding.root,
                            resultMap["message"].toString(),
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(
                            Color.GREEN
                        ).show()
                    } else {
                        Snackbar.make(
                            binding.root,
                            resultMap["message"].toString(),
                            Snackbar.LENGTH_LONG
                        ).setBackgroundTint(
                            Color.RED
                        ).show()
                    }
                } else {
                    Snackbar.make(binding.root, "Password doesn't match", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(
                            Color.RED
                        ).show()
                }
            }
        }
    }
}