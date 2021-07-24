package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ActivityMainAdminBinding

class MainAdminActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainAdminBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_admin)
        setSupportActionBar(binding.adminMainToolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main_admin) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayoutAdminMain)
        binding.navViewMainAdmin.setupWithNavController(navController)
        binding.adminMainToolbar.setupWithNavController(navController,appBarConfiguration)

        viewModel = ViewModelProvider(this).get(MainAdminViewModel::class.java)
    }


}