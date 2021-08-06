package com.thuctaptotnghiep.doantttn.di

import com.thuctaptotnghiep.doantttn.MainActivityViewModel
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterViewModel
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModel::class, DatabaseModel::class])
interface DataComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(loginAndRegisterViewModel: LoginAndRegisterViewModel)
    fun inject(mainAdminViewModel: MainAdminViewModel)
    fun inject(mainGuestViewModel: MainGuestViewModel)
}