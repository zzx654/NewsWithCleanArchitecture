package com.example.newswithcleanarchitecture.breaking_news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.GetBreakingNewsUseCase
import com.example.domain.util.Resource
import com.example.newswithcleanarchitecture.NewsFromApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(NewsFromApiState())
    val state: State<NewsFromApiState>
        get() = _state

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() {
        viewModelScope.launch {
            getBreakingNewsUseCase()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value =
                                NewsFromApiState(
                                    articles = result.data?.articles ?: emptyList()
                                )
                        }
                        is Resource.Error -> {
                            _state.value = NewsFromApiState(
                                error = result.message ?: "An unexpected error occured"
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = NewsFromApiState(isLoading = true)
                        }
                    }
                }
        }
    }
}