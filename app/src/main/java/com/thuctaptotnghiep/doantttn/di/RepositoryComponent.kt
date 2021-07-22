package com.thuctaptotnghiep.doantttn.di

import com.thuctaptotnghiep.doantttn.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModel::class])
interface RepositoryComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
}