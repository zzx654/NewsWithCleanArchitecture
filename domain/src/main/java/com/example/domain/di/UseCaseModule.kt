package com.example.domain.di

import com.example.domain.repository.NewsRepository
import com.example.domain.use_case.*
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
    @Provides
    @Singleton
    fun provideDatabaseUseCases(repository: NewsRepository): DatabaseUseCases {
        return DatabaseUseCases(
            getArticles = GetArticles(repository),
            deleteArticle = DeleteArticle(repository),
            addArticle = AddArticle(repository),
            findArticle = FindArticle(repository)
        )
    }
}