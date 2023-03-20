package com.example.newswithcleanarchitecture.util

sealed class UiEvent {
    data class ShowToast(val message: String): UiEvent()
}
