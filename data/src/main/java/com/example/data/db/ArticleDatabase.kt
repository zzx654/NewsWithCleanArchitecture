package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.domain.model.Article

@Database(
    entities=[Article::class],
    version=1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract val articleDao: ArticleDao

    companion object{
        const val DATABASE_NAME = "articles_db"
    }
}