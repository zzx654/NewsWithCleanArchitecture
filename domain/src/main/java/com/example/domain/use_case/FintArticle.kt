package com.example.domain.use_case

import com.example.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class FindArticle(
    private val repository: NewsRepository
) {
    operator fun invoke(url: String): Flow<Int> = repository.findArticle(url)
}