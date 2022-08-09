package com.example.mvvmcleanarchitecturedemo.data.db

import androidx.room.*
import com.example.mvvmcleanarchitecturedemo.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM Article")
    fun getSavedNews(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article): Int
}