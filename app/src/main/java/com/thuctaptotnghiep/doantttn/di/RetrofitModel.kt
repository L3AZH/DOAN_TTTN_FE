package com.thuctaptotnghiep.doantttn.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.api.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RetrofitModel {

    @Singleton
    @Provides
    fun providerLog(): HttpLoggingInterceptor {
        val log = HttpLoggingInterceptor()
        log.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        return log
    }

    @Singleton
    @Provides
    fun providerClient(log: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(log).build()
    }

    @Singleton
    @Provides
    fun providerGson():Gson{
        return GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create()
    }

    @Singleton
    @Provides
    fun providerRetrofitInstance(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build()
    }

    @Singleton
    @Provides
    fun providerRetrofitServiceInterface(retrofit: Retrofit):ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }

}