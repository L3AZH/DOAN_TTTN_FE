package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ActivityMainGuestBinding
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterActivity

class MainGuestActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainGuestBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_guest)
        setSupportActionBar(binding.guestMainToolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main_guest) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration =
            AppBarConfiguration(navController.graph, binding.drawerLayoutGuestMain)
        binding.navViewMainGuest.setupWithNavController(navController)
        binding.bottomNavigationViewGuest.setupWithNavController(navController)
        binding.guestMainToolbar.setupWithNavController(navController, appBarConfiguration)

        viewModel = ViewModelProvider(this).get(MainGuestViewModel::class.java)
        setOnlickLogoutOptionOnSildeMenu()
    }

    fun setOnlickLogoutOptionOnSildeMenu() {
        binding.navViewMainGuest.menu.findItem(R.id.logoutOption)
            .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    val prefs =
                        getSharedPreferences(Constant.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        remove("token")
                        remove("role")
                        remove("refreshToken")
                    }.apply()
                    val goToLoginAndRegisterScreen =
                        Intent(this@MainGuestActivity, LoginAndRegisterActivity::class.java)
                    startActivity(goToLoginAndRegisterScreen)
                    return true
                }

            })
    }
}