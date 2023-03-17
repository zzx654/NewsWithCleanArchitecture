package com.example.data.remote.dto

import com.example.domain.model.Article
import com.example.domain.model.NewsResponse

data class  NewsResponseDto(
    val articles: MutableList<ArticleDto>,
    val status: String,
    val totalResults: Int
)
fun NewsResponseDto.toNewsResponse(articles: MutableList<Article> ) : NewsResponse {
    return NewsResponse(
        articles = articles,
        totalResults
    )
}