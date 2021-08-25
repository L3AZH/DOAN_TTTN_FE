package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ActivityMainAdminBinding
import com.thuctaptotnghiep.doantttn.databinding.NavHeaderMainAdminBinding
import com.thuctaptotnghiep.doantttn.dialog.ChangePasswordDialog
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterActivity

class MainAdminActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainAdminBinding
    lateinit var headerNavViewBinding: NavHeaderMainAdminBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_admin)
        setSupportActionBar(binding.adminMainToolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main_admin) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration(navController.graph, binding.drawerLayoutAdminMain)
        binding.navViewMainAdmin.setupWithNavController(navController)
        binding.adminMainToolbar.setupWithNavController(navController, appBarConfiguration)

        headerNavViewBinding =
            NavHeaderMainAdminBinding.bind(binding.navViewMainAdmin.getHeaderView(0))

        viewModel = ViewModelProvider(this).get(MainAdminViewModel::class.java)

        setLogoutOptionAdminClick()
        setChangePasswordOptionAdminClick()
        setOnAvatarClick()
    }

    fun setLogoutOptionAdminClick() {
        binding.navViewMainAdmin.menu.findItem(R.id.logoutOptionAdmin)
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
                        Intent(this@MainAdminActivity, LoginAndRegisterActivity::class.java)
                    startActivity(goToLoginAndRegisterScreen)
                    return true
                }
            })
    }

    fun setChangePasswordOptionAdminClick() {
        binding.navViewMainAdmin.menu.findItem(R.id.changePasswordOptionAdmin)
            .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    val showChangePasswordDialog = ChangePasswordDialog(viewModel, null)
                    showChangePasswordDialog.show(supportFragmentManager, "change pass dialog")
                    return true
                }
            })
    }

    fun setOnAvatarClick() {
        headerNavViewBinding.circleImageNavHeadAdmin.setOnClickListener {
            //Snackbar.make(binding.root, "This will be infomation", Snackbar.LENGTH_LONG).show()
        }
    }


}

