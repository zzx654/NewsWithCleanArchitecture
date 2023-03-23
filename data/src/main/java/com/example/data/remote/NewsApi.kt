package com.example.data.remote

import com.example.data.Constants.API_KEY
import com.example.data.remote.dto.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countrycode:String="us",
        @Query("page")
        pageNumber:Int,
        @Query("apiKey")
        apiKey:String=API_KEY
    ): Response<NewsResponseDto>

    @GET("v2/everyThing")
    suspend fun getSearchedNews(
        @Query("q")
        searchQuery:String="kr",
        @Query("page")
        pageNumber:Int,
        @Query("apiKey")
        apiKey:String=API_KEY
    ):Response<NewsResponseDto>
}