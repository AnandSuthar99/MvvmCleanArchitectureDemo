package com.example.mvvmcleanarchitecturedemo.domain.repository

import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String, query: String, page: Int): Resource<APIResponse>
    fun getSavedNews(): Flow<List<Article>>
    suspend fun deleteNews(article: Article): Int
    suspend fun saveNews(article: Article)
}