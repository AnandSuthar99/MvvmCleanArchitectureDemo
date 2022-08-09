package com.example.mvvmcleanarchitecturedemo.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.mvvmcleanarchitecturedemo.BuildConfig
import com.example.mvvmcleanarchitecturedemo.data.api.NewsApiService
import com.example.mvvmcleanarchitecturedemo.data.db.ArticleDatabase
import com.example.mvvmcleanarchitecturedemo.data.repository.NewsRepositoryImpl
import com.example.mvvmcleanarchitecturedemo.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import com.example.mvvmcleanarchitecturedemo.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import com.example.mvvmcleanarchitecturedemo.data.util.CustomDateTypeAdapter
import com.example.mvvmcleanarchitecturedemo.domain.repository.NewsRepository
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class GetNewsHeadlinesUseCaseTest {
    private lateinit var roomDatabase: ArticleDatabase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: NewsRepository
    private lateinit var newsApiService: NewsApiService

    private lateinit var gson: Gson

    @Before
    fun init() {
        gson = GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, CustomDateTypeAdapter)
        }.create()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()

        newsApiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build().create(NewsApiService::class.java)

        roomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        repository = NewsRepositoryImpl(
            NewsRemoteDataSourceImpl(newsApiService),
            NewsLocalDataSourceImpl(roomDatabase.getArticleDao())
        )
    }

    @Test
    fun getNewsHeadlines_requestSent_validateResponseStatusAndTotalResults() {
        val response = runBlocking { repository.getNewsHeadlines("us", 1) }
        Truth.assertThat(response.data?.status).isEqualTo("ok")
        Truth.assertThat(response.data?.totalResults).isGreaterThan(0)
    }

    @After
    fun tearDown() {
    }
}