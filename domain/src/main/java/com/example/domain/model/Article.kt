package com.example.domain.model

data class Article(
    var id: Int? = null,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)
