package com.thuctaptotnghiep.doantttn

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.databinding.ActivityMainBinding
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setUp()
    }

    fun setUp() {
        ///UI working  too much on this suspend function
        val pref = getSharedPreferences(Constant.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val token = pref.getString("token", "null")
        val role = pref.getString("role", "null")
        val refreshToken = pref.getString("refreshToken","null")
        Log.i("token", token!!)
        Log.i("role", role!!)
        CoroutineScope(Dispatchers.Default).launch {
            //delay(5000)
            if (token == "null") {
                val goToLoginActivity =
                    Intent(this@MainActivity, LoginAndRegisterActivity::class.java)
                startActivity(goToLoginActivity)
            } else {
                if (viewModel.checkTokenNotExpire(token).await()) {
                    when (role) {
                        "admin" -> {
                            val goToMainScreenAdmin =
                                Intent(this@MainActivity, MainAdminActivity::class.java)
                            startActivity(goToMainScreenAdmin)
                        }
                        "guest" -> {
                            val goToMainScreenGuest =
                                Intent(this@MainActivity, MainGuestActivity::class.java)
                            startActivity(goToMainScreenGuest)
                        }
                        "null" -> {
                            Snackbar.make(
                                binding.root,
                                "Some thing was wrong, Logout",
                                Snackbar.LENGTH_LONG
                            ).apply {
                                setBackgroundTint(Color.RED)
                            }.show()
                            val goToLoginActivity =
                                Intent(this@MainActivity, LoginAndRegisterActivity::class.java)
                            startActivity(goToLoginActivity)
                        }
                    }
                } else {
                    val updateTokenResult = viewModel.getNewToken(refreshToken!!).await()
                    if ((updateTokenResult["flag"] as Boolean)) {
                        pref.edit().putString("token", updateTokenResult["token"].toString())
                            .apply()
                        when (pref.getString("role", "null")) {
                            "admin" -> {
                                val goToMainScreenAdmin =
                                    Intent(this@MainActivity, MainAdminActivity::class.java)
                                startActivity(goToMainScreenAdmin)
                            }
                            "guest" -> {
                                val goToMainScreenGuest =
                                    Intent(this@MainActivity, MainGuestActivity::class.java)
                                startActivity(goToMainScreenGuest)
                            }
                            "null" -> {
                                Snackbar.make(
                                    binding.root,
                                    "Some thing was wrong, Logout",
                                    Snackbar.LENGTH_LONG
                                ).apply {
                                    setBackgroundTint(Color.RED)
                                }.show()
                                val goToLoginActivity =
                                    Intent(this@MainActivity, LoginAndRegisterActivity::class.java)
                                startActivity(goToLoginActivity)
                            }
                        }
                    } else {
                        Snackbar.make(
                            binding.root, updateTokenResult["message"].toString(),
                            Snackbar.LENGTH_LONG
                        ).apply {
                            setBackgroundTint(Color.RED)
                        }.show()
                        val goToLoginActivity =
                            Intent(this@MainActivity, LoginAndRegisterActivity::class.java)
                        startActivity(goToLoginActivity)
                    }
                }
            }
        }
    }
}