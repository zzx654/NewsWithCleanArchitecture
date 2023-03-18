package com.example.newswithcleanarchitecture.util

interface NewsPaginator<Key,Item> {
    suspend fun loadNextItems()
    fun reset()
}