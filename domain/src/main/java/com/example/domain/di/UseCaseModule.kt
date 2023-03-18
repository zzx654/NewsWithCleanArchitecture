package com.example.domain.di

import com.example.domain.repository.NewsRepository
import com.example.domain.use_case.BreakingNewsUseCase
import com.example.domain.use_case.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideBreakingNewsUseCase(repository: NewsRepository): BreakingNewsUseCase {
        return BreakingNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchgNewsUseCase(repository: NewsRepository): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }
}