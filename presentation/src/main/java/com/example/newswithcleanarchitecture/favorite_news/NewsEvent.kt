package com.example.newswithcleanarchitecture.favorite_news

import com.example.domain.model.Article

sealed class NewsEvent {
    data class DeleteArticle(val article: Article): NewsEvent()
    object RestoreArticle: NewsEvent()
}
