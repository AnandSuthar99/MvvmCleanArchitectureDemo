package com.example.mvvmcleanarchitecturedemo.domain.usecase

import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import com.example.mvvmcleanarchitecturedemo.data.util.Resource
import com.example.mvvmcleanarchitecturedemo.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(country: String, query: String, page: Int): Resource<APIResponse> {
        return repository.getSearchedNews(country, query, page)
    }
}