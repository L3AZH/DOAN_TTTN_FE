package com.thuctaptotnghiep.doantttn

import android.app.Application
import com.thuctaptotnghiep.doantttn.di.DaggerDataComponent
import com.thuctaptotnghiep.doantttn.di.DataComponent
import com.thuctaptotnghiep.doantttn.di.DatabaseModel

class App : Application() {
    private lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()
        dataComponent =
            DaggerDataComponent.builder().databaseModel(DatabaseModel(applicationContext)).build();
    }

    fun getDataComponent(): DataComponent {
        return dataComponent
    }
}