package com.thuctaptotnghiep.doantttn

import android.app.Application
import com.thuctaptotnghiep.doantttn.di.DaggerRepositoryComponent
import com.thuctaptotnghiep.doantttn.di.RepositoryComponent

class App:Application() {
    private lateinit var repositoryComponent:RepositoryComponent

    override fun onCreate() {
        super.onCreate()
        repositoryComponent = DaggerRepositoryComponent.create();
    }

    fun getRepositoryComponent():RepositoryComponent{
        return repositoryComponent
    }
}