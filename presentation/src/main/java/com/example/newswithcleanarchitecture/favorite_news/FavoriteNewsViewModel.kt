package com.example.newswithcleanarchitecture.favorite_news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Article
import com.example.domain.use_case.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases
): ViewModel() {

    private val _state = mutableStateOf(emptyList<Article>())
    val state: State<List<Article>> = _state

    private var getArticlesJob: Job? = null

    init {
        getArticles()
    }

    private fun getArticles() {
        getArticlesJob?.cancel()
        getArticlesJob = databaseUseCases.getArticles()
            .onEach { articles ->
                _state.value = articles
            }
            .launchIn(viewModelScope)
    }

}