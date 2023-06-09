package com.example.domain.use_case

import com.example.domain.model.NewsResponse
import com.example.domain.repository.NewsRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(searchQuery: String, page: Int): Flow<Resource<NewsResponse>> = repository.getSearchedNews(searchQuery, page)
}