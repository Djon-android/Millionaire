package com.example.covertervk.di

import android.provider.SyncStateContract
import com.example.covertervk.data.remote.ExchangeApi
import com.example.covertervk.data.repository.ValueRepositoryImpl
import com.example.covertervk.domain.repository.ValueRepository
import com.example.covertervk.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExchangeApi(): ExchangeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideValueRepository(api: ExchangeApi): ValueRepository {
        return ValueRepositoryImpl(api)
    }
}