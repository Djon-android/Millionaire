package com.example.millionaire.di

import com.example.millionaire.data.repository.QuestionsRepositoryImpl
import com.example.millionaire.data.repository.RecordsRepositoryImpl
import com.example.millionaire.domain.repository.QuestionsRepository
import com.example.millionaire.domain.repository.RecordsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindQuestionsRepository(impl: QuestionsRepositoryImpl): QuestionsRepository

    @Binds
    @Singleton
    fun bindRecordsRepository(impl: RecordsRepositoryImpl): RecordsRepository
}