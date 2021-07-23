package com.thuctaptotnghiep.doantttn.ui.LoginRegister.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.FragmentRegisterBinding
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterActivity
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterVieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    lateinit var viewmodel: LoginAndRegisterVieModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewmodel = (activity as LoginAndRegisterActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setOnclickRegisterBtn()
        setOnBackBtn()
    }

    fun initViewModel() {
        viewmodel.registerResult.observe(viewLifecycleOwner, Observer { result ->
            if ((result["flag"] as Boolean)) {
                binding.passwordRegisterEditText.setText("")
                binding.confirmPasswordEditText.setText("")
                binding.confirmPasswordEditText.error = ""
                Snackbar.make(binding.root, result["message"].toString(), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.GREEN).show()
            } else {
                Snackbar.make(binding.root, result["message"].toString(), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED).show()
            }
        })
    }

    fun setOnclickRegisterBtn() {
        binding.registerBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                if (viewmodel.validationBeforeRegister(
                        binding.passwordRegisterEditText.text.toString(),
                        binding.confirmPasswordEditText.text.toString()
                    )
                ) {
                    viewmodel.register(
                        binding.emailRegisterEditText.text.toString(),
                        binding.confirmPasswordEditText.text.toString()
                    )
                }
                else{
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.confirmPasswordEditText.error = "Password doesn't match !!"
                    }
                }
            }
        }
    }

    fun setOnBackBtn(){
        binding.backToLoginBtn.setOnClickListener {
            val goBackToLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(goBackToLogin)
        }
    }
}