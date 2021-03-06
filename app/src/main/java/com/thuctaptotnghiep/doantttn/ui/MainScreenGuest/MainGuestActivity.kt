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
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ActivityMainGuestBinding
import com.thuctaptotnghiep.doantttn.databinding.NavHeaderMainGuestBinding
import com.thuctaptotnghiep.doantttn.dialog.ChangePasswordDialog
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterActivity

class MainGuestActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainGuestBinding
    lateinit var headerNavBinding: NavHeaderMainGuestBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var navController: NavController

    private var isCloseAppFlag = false

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

        headerNavBinding = NavHeaderMainGuestBinding.bind(binding.navViewMainGuest.getHeaderView(0))

        viewModel = ViewModelProvider(this).get(MainGuestViewModel::class.java)

        setOnlickLogoutOptionOnSildeMenu()
        setOnClickChangePasswordOptionSildeMenu()
        setOnAvatarClick()
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

    fun setOnClickChangePasswordOptionSildeMenu() {
        binding.navViewMainGuest.menu.findItem(R.id.changePasswordOption)
            .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    val showChangePasswordDialog = ChangePasswordDialog(null,viewModel)
                    showChangePasswordDialog.show(supportFragmentManager,"change pass dialog")
                    return true
                }
            })
    }

    fun setOnAvatarClick(){
        headerNavBinding.circleImageNavHeadGuest.setOnClickListener {

        }
    }

    override fun onBackPressed() {
        if(isCloseAppFlag){
            val homeItent = Intent(Intent.ACTION_MAIN)
            homeItent.addCategory(Intent.CATEGORY_HOME)
            homeItent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(homeItent)
        }
        else{
            Snackbar.make(binding.root,"Press Back agian to exist", Snackbar.LENGTH_SHORT).show()
            isCloseAppFlag = true
        }
    }

    override fun onStop() {
        super.onStop()
        isCloseAppFlag = false
    }
}