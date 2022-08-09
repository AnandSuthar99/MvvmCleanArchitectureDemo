package com.example.mvvmcleanarchitecturedemo.domain.usecase

import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import com.example.mvvmcleanarchitecturedemo.data.util.Resource
import com.example.mvvmcleanarchitecturedemo.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page)
    }
}