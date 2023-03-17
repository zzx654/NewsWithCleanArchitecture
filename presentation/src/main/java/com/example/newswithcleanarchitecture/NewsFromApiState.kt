package com.example.newswithcleanarchitecture

import com.example.domain.model.Article

data class NewsFromApiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)
