package com.example.domain.model

data class NewsResponse(
    val articles: MutableList<Article>,
    val totalResults: Int
)
