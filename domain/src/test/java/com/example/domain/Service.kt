package com.example.domain

import android.provider.SyncStateContract
import com.example.domain.model.Article
import com.example.domain.model.NewsResponse
import com.example.domain.model.Source
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countrycode:String="us",
        @Query("page")
        pageNumber:Int,
        @Query("apiKey")
        apiKey:String=API_KEY
    ): Response<NewsResponseDto>

    companion object {
        const val API_KEY = "54b745a47457487695b6c2def5a69be6"
        const val BASE_URL = "https://newsapi.org"

         private val service = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)

        fun getService() = service

    }
}
data class  NewsResponseDto(
    val articles: MutableList<ArticleDto>,
    val status: String,
    val totalResults: Int
)
fun NewsResponseDto.toNewsResponse(articles: MutableList<Article> ) : NewsResponse {
    return NewsResponse(
        articles = articles,
        totalResults
    )
}

data class ArticleDto(
    var id: Int? = null,
    val author: String? = null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)

fun ArticleDto.toArticle() : Article {
    return Article(
        id = id,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}
