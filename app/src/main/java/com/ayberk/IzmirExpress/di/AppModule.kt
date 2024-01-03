package com.ayberk.IzmirExpress.di

import com.ayberk.IzmirExpress.retrofit.RetrofitInstance
import com.ayberk.IzmirExpress.retrofit.RetrofitRepository
import com.ayberk.IzmirExpress.util.Constans.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providerRetrofitRepository(
        api: RetrofitInstance
    ) = RetrofitRepository(api)

    @Singleton
    @Provides
    fun provideIzmirApi():RetrofitInstance{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitInstance::class.java)
    }
}