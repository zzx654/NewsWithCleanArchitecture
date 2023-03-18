package com.example.newswithcleanarchitecture.breaking_news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.GetBreakingNewsUseCase
import com.example.newswithcleanarchitecture.util.NewsFromApiState
import com.example.newswithcleanarchitecture.NewsFromApiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    ) : NewsFromApiViewModel() {

    private val _state = mutableStateOf(NewsFromApiState())
    override val state: State<NewsFromApiState>
        get() = _state
    override val newsPaginator = BreakingNewsPaginator(
        getBreakingNewsUseCase = getBreakingNewsUseCase,
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
                endReached = items.isEmpty() || currentPage == totalPages
            )
        }
    )

    init {
        loadNextItems()
    }
    override fun loadNextItems() {
        viewModelScope.launch {
            newsPaginator.loadNextItems()
        }
    }
}
