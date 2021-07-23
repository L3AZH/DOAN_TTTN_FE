package com.thuctaptotnghiep.doantttn.di

import com.thuctaptotnghiep.doantttn.MainActivityViewModel
import com.thuctaptotnghiep.doantttn.ui.LoginRegister.LoginAndRegisterVieModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModel::class])
interface RepositoryComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(loginAndRegisterVieModel: LoginAndRegisterVieModel)
}