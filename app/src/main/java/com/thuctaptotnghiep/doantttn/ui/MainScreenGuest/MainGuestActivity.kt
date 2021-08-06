package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ActivityMainGuestBinding

class MainGuestActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainGuestBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_guest)
        setSupportActionBar(binding.guestMainToolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main_guest) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayoutGuestMain)
        binding.navViewMainGuest.setupWithNavController(navController)
        binding.bottomNavigationViewGuest.setupWithNavController(navController)
        binding.guestMainToolbar.setupWithNavController(navController,appBarConfiguration)

        viewModel = ViewModelProvider(this).get(MainGuestViewModel::class.java)
    }
}