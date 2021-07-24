package com.thuctaptotnghiep.doantttn.ui.LoginRegister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ActivityLoginAndRegisterBinding

class LoginAndRegisterActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginAndRegisterBinding
    lateinit var viewModel: LoginAndRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_and_register)
        viewModel = ViewModelProvider(this).get(LoginAndRegisterViewModel::class.java)
        binding.lifecycleOwner = this
    }
}