package com.example.mvvmcleanarchitecturedemo.data.repository.datasource

import com.example.mvvmcleanarchitecturedemo.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
    fun getSavedArticles(): Flow<List<Article>>
    suspend fun deleteArticle(article: Article): Int
}