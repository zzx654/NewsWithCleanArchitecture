package com.example.domain.use_case

import com.example.domain.model.Article
import com.example.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> = repository.getArticles()

}