package com.example.domain.use_case

import com.example.domain.model.Article
import com.example.domain.repository.NewsRepository

class DeleteArticle(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        repository.deleteArticle(article)
    }
}