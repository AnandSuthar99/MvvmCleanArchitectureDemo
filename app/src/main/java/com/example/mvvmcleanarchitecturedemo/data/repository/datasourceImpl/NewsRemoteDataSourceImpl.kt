package com.example.mvvmcleanarchitecturedemo.data.repository.datasourceImpl

import com.example.mvvmcleanarchitecturedemo.data.api.NewsApiService
import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import com.example.mvvmcleanarchitecturedemo.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService
) : NewsRemoteDataSource {

    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsApiService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedTopHeadlines(
        country: String,
        query: String,
        page: Int
    ): Response<APIResponse> {
        return newsApiService.getSearchedTopHeadlines(country, query, page)
    }
}