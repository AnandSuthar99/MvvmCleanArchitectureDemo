package com.example.mvvmcleanarchitecturedemo.domain.usecase

import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(article: Article): Int {
        return repository.deleteNews(article)
    }
}