package com.example.mvvmcleanarchitecturedemo.data.repository.datasource

import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>
    suspend fun getSearchedTopHeadlines(
        country: String,
        query: String,
        page: Int
    ): Response<APIResponse>
}