package com.example.domain.repository

import com.example.domain.model.NewsResponse
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getBreakingNews(page: Int? = 1) : Flow<Resource<NewsResponse>>
}