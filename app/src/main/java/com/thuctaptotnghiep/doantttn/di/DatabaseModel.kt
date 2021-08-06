package com.thuctaptotnghiep.doantttn.di

import android.content.Context
import com.thuctaptotnghiep.doantttn.db.DatabaseInstance
import com.thuctaptotnghiep.doantttn.db.DbDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModel(val context: Context) {

    @Singleton
    @Provides
    fun providerContext():Context{
        return context
    }

    @Singleton
    @Provides
    fun providerDatabaInstance(context: Context): DatabaseInstance {
        return DatabaseInstance.getDbInstance(context)
    }

    @Singleton
    @Provides
    fun getDbDao(dbInstance: DatabaseInstance): DbDao {
        return dbInstance.getDbDao()
    }
}