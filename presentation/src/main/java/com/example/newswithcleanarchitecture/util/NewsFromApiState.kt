package com.example.newswithcleanarchitecture.util

import com.example.domain.model.Article

data class NewsFromApiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1,
    val searchQuery: String = ""
)
