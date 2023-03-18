package com.example.newswithcleanarchitecture

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.domain.model.Article
import com.example.newswithcleanarchitecture.util.NewsFromApiState
import com.example.newswithcleanarchitecture.util.NewsPaginator

abstract class NewsFromApiViewModel: ViewModel() {

    abstract val state: State<NewsFromApiState>

    abstract val newsPaginator: NewsPaginator<Int, Article>

    abstract fun loadNextItems()
}