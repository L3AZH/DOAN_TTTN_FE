package com.thuctaptotnghiep.doantttn.ui.LoginRegister.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.FragmentLoginBinding
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterActivity
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterViewModel
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginAndRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel = (activity as LoginAndRegisterActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setOnClickLoginBtn()
        setLinkTextToRegister()
    }

    fun initViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner, { result ->
            if ((result["flag"] as Boolean)) {
                val prefs = requireActivity().getSharedPreferences(
                    Constant.SHARE_PREFERENCE_NAME,
                    Context.MODE_PRIVATE
                )
                prefs.edit().apply {
                    putString("token", "Bearer " + result["token"].toString())
                    putString("refreshToken",result["refreshToken"].toString())
                    putString("email",binding.emailTextInputEditText.text.toString())
                    putString("idAccount",result["idAccount"].toString())
                }.apply()
                if (result["role"].toString() == "admin") {
                    prefs.edit().putString("role", result["role"].toString()).apply()
                    val goToMainScreenAdmin =
                        Intent(requireActivity(), MainAdminActivity::class.java)
                    startActivity(goToMainScreenAdmin)
                } else {
                    prefs.edit().putString("role", result["role"].toString()).apply()
                    val goToMainScreenGuest =
                        Intent(requireActivity(), MainGuestActivity::class.java)
                    startActivity(goToMainScreenGuest)
                }
            } else {
                Snackbar.make(binding.root, result["message"].toString(), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        Color.RED
                    ).show()
            }
        })
    }

    fun setOnClickLoginBtn() {
        binding.loginBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                viewModel.login(
                    binding.emailTextInputEditText.text.toString(),
                    binding.passwordTextInputEditText.text.toString()
                )
            }
        }
    }

    fun setLinkTextToRegister() {
        val linkText = Link("Register").apply {
            bold = true
            textColor = Color.BLACK
            underlined = false
            setOnClickListener {
                val goToRegisterFragment =
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(goToRegisterFragment)
            }
        }
        binding.registerTextView.applyLinks(linkText)
    }
}