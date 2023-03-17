package com.example.data.remote.dto

import com.example.domain.model.Article
import com.example.domain.model.Source

data class ArticleDto(
    var id: Int? = null,
    val author: String? = null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)

fun ArticleDto.toArticle() : Article {
    return Article(
        id = id,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}
