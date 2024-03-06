package com.example.millionaire.di

import com.example.millionaire.data.repository.QuestionsRepositoryImpl
import com.example.millionaire.domain.repository.QuestionsRepository
import dagger.Binds
import javax.inject.Singleton

interface RepositoryModule {

    @Binds
    @Singleton
    fun bindQuestionsRepository(impl: QuestionsRepositoryImpl): QuestionsRepository
}