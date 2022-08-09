package com.example.mvvmcleanarchitecturedemo.data.repository

import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.data.repository.datasource.NewsLocalDataSource
import com.example.mvvmcleanarchitecturedemo.data.repository.datasource.NewsRemoteDataSource
import com.example.mvvmcleanarchitecturedemo.data.util.Resource
import com.example.mvvmcleanarchitecturedemo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(msg = response.message())
    }

    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> =
        responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))


    override suspend fun getSearchedNews(
        country: String,
        query: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(
            newsRemoteDataSource.getSearchedTopHeadlines(
                country,
                query,
                page
            )
        )
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }

    override suspend fun deleteNews(article: Article): Int {
        return newsLocalDataSource.deleteArticle(article)
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }
}