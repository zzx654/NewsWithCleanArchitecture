package com.example.newswithcleanarchitecture.search_news

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.data.Constants.SEARCH_NEWS_TIME_DELAY
import com.example.domain.use_case.SearchNewsUseCase
import com.example.newswithcleanarchitecture.NewsFromApiViewModel
import com.example.newswithcleanarchitecture.util.NewsFromApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val getSearchedNewsUseCase: SearchNewsUseCase
) : NewsFromApiViewModel() {
    private var searchJob: Job? = null
    private val _state = mutableStateOf(NewsFromApiState())
    override val state: State<NewsFromApiState>
        get() = _state
    override val newsPaginator = SearchNewsPaginator(
        getSearchedNewsUseCase = getSearchedNewsUseCase,
        initialKey = state.value.page,
        onLoadUpdated = {
            _state.value = state.value.copy(isLoading = it)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = { message ->
            _state.value = state.value.copy(error = message)
        },
        onSuccess = { items, currentPage, newKey, totalPages ->
            _state.value = state.value.copy(
                articles = state.value.articles + items,
                page = newKey,
                endReached = items.isEmpty() || currentPage == totalPages//새로불러운 아이템이 비어있으면 마지막에 다다른것
            )
        }
    )

    @SuppressLint("SuspiciousIndentation")
    fun searchQueryChange(queryString: String) {
        newsPaginator.setSearchQuery(queryString)
        _state.value = state.value.copy(searchQuery = queryString, articles = emptyList(), endReached = false)
        searchJob?.cancel()
        if(queryString != "") {
            searchJob = viewModelScope.launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                loadNextItems()
            }
        }
    }

    override fun loadNextItems() {
        viewModelScope.launch {
            newsPaginator.loadNextItems()
        }
    }
}