package com.example.newswithcleanarchitecture.article_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Article
import com.example.domain.use_case.DatabaseUseCases
import com.example.newswithcleanarchitecture.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(false)
    val state: State<Boolean> = _state

    private var articleUrl: String? = null

    private var checkArticleJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()



    init{
        articleUrl = savedStateHandle.get<String>("url")
        articleUrl?.let { isArticleExist(it) }
    }
    fun toggleSave(article: Article) {
        articleUrl?.let { url ->
            viewModelScope.launch {
                if(state.value) {
                    databaseUseCases.deleteArticle(article)
                    _eventFlow.emit(UiEvent.ShowToast("삭제되었습니다"))
                }
                else {
                    databaseUseCases.addArticle(article)
                    _eventFlow.emit(UiEvent.ShowToast("저장되었습니다"))
                }

            }
        }
    }
    private fun isArticleExist(url: String) {
        checkArticleJob?.cancel()
        checkArticleJob = databaseUseCases.findArticle(url)
            .onEach { count ->
                _state.value = count!=0
            }
            .launchIn(viewModelScope)
    }
}