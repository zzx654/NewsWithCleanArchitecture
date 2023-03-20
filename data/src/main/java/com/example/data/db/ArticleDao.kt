package com.example.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long

    @Query("SELECT *FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT COUNT(*) FROM articles WHERE url = :url")
    fun findArticle(url: String): Flow<Int>
}