package com.example.mvvmcleanarchitecturedemo.domain.usecase

import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val repository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return repository.getSavedNews()
    }
}