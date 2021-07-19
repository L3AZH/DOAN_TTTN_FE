package com.thuctaptotnghiep.doantttn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.thuctaptotnghiep.doantttn.databinding.ActivityMainBinding
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

    }

    fun setUp(){
        val pref = getSharedPreferences("com.thuctaptotnghiep.doantttn",Context.MODE_PRIVATE)
        val token = pref.getString("accessToken","null")
        if(token.equals("null")){
            val goToLoginActivity = Intent(this@MainActivity,LoginAndRegisterActivity::class.java)
            startActivity(goToLoginActivity)
        }
        else{

        }
    }
}