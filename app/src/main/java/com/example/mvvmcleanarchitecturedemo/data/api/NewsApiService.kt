package com.example.mvvmcleanarchitecturedemo.data.api

import com.example.mvvmcleanarchitecturedemo.BuildConfig
import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>

    @GET("/v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query("country") country: String = "us",
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>
}