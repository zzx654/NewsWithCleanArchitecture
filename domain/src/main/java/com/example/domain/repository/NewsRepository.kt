package com.example.domain.repository

import com.example.domain.model.Article
import com.example.domain.model.NewsResponse
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getBreakingNews(page: Int = 1) : Flow<Resource<NewsResponse>>

    suspend fun getSearchedNews(searchQuery: String, page: Int): Flow<Resource<NewsResponse>>

    fun getArticles(): Flow<List<Article>>

    suspend fun insertArticle(article: Article)

    suspend fun deleteArticle(url: String)

    fun findArticle(url: String): Flow<Int>

}