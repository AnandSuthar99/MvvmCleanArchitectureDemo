package com.example.mvvmcleanarchitecturedemo.data.api

import com.example.mvvmcleanarchitecturedemo.BuildConfig
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var newsApiService: NewsApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        newsApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputString = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputString.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }

    // To check if the request path is correct and response body is not null
    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsApiService.getTopHeadlines("us", 1).body()
            val request = mockWebServer.takeRequest()
            Truth.assertThat(request.path)
                .isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=${BuildConfig.API_KEY}")
            Truth.assertThat(responseBody).isNotNull()
        }
    }

    // Check whether the response received has 20 articles
    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsApiService.getTopHeadlines("us", 1).body()
            Truth.assertThat(responseBody!!.articles.size).isEqualTo(20)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}