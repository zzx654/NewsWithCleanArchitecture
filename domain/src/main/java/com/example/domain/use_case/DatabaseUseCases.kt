package com.example.domain.use_case

data class DatabaseUseCases(
    val getArticles: GetArticles,
    val deleteArticle: DeleteArticle,
    val addArticle: AddArticle,
    val findArticle: FindArticle
)