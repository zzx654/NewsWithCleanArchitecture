package com.example.data.repository

import com.example.data.remote.NewsApi
import com.example.data.remote.dto.toArticle
import com.example.data.remote.dto.toNewsResponse
import com.example.domain.model.Article
import com.example.domain.model.NewsResponse
import com.example.domain.repository.NewsRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getBreakingNews(page: Int?): Flow<Resource<NewsResponse>> {

        return flow {
            try {
                emit(Resource.Loading<NewsResponse>())
                api.getBreakingNews().body()?.let{ response ->
                    response.articles.let { articles ->
                        val newsResponse =
                            response.toNewsResponse(articles.map { it.toArticle() }.toMutableList())
                        emit(Resource.Success<NewsResponse>(newsResponse))
                    }
                }
            } catch(e: HttpException) {
                emit(Resource.Error<NewsResponse>(e.localizedMessage ?: "An unexpected error occured"))

            } catch(e: IOException) {
                emit(Resource.Error<NewsResponse>("Couldn't reach server. Check your internet connection"))
            }
        }

    }

    override suspend fun getSearchedNews(
        searchQuery: String,
        page: Int
    ): Flow<Resource<NewsResponse>> {
        return flow {
            try {
                emit(Resource.Loading<NewsResponse>())
                val newsResponse = api.getSearchedNews(searchQuery = searchQuery, pageNumber = page).body()
                if(newsResponse == null) {
                    emit(Resource.Success<NewsResponse>(NewsResponse(articles = emptyList<Article>().toMutableList(), totalResults = 0)))
                } else {
                    newsResponse.articles.let { articles ->
                        val newsResponse = newsResponse.toNewsResponse( articles.map { it.toArticle() }.toMutableList())
                        emit(Resource.Success<NewsResponse>(newsResponse))
                    }
                }
            } catch(e: HttpException) {
                emit(Resource.Error<NewsResponse>(e.localizedMessage ?: "An unexpected error occured"))

            } catch(e: IOException) {
                emit(Resource.Error<NewsResponse>("Couldn't reach server. Check your internet connection"))
            }
        }
    }

}