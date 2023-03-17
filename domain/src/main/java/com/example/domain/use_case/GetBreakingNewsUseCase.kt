package com.example.domain.use_case

import com.example.domain.model.NewsResponse
import com.example.domain.repository.NewsRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreakingNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(): Flow<Resource<NewsResponse>> = repository.getBreakingNews()
}