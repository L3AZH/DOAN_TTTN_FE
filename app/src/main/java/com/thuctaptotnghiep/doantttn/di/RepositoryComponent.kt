package com.thuctaptotnghiep.doantttn.di

import com.thuctaptotnghiep.doantttn.MainActivityViewModel
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterViewModel
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModel::class])
interface RepositoryComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(loginAndRegisterViewModel: LoginAndRegisterViewModel)
    fun inject(mainAdminViewModel: MainAdminViewModel)
}