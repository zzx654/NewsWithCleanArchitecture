package com.example.domain.repository

import com.example.domain.Service.Companion.getService
import com.example.domain.model.Article
import com.example.domain.model.NewsResponse
import com.example.domain.toArticle
import com.example.domain.toNewsResponse
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FakeNewsRepository : NewsRepository {
    override suspend fun getBreakingNews(page: Int): Flow<Resource<NewsResponse>> {
        return flow {
            try {
                getService().getBreakingNews(pageNumber = 1).body()?.let { response ->
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

        }

    }

    override fun getArticles(): Flow<List<Article>> {
        return flow {
        }
    }

    override suspend fun insertArticle(article: Article) {

    }

    override suspend fun deleteArticle(url: String) {

    }

    override fun findArticle(url: String): Flow<Int> {
        return flow {

        }

    }
}