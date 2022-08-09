package com.example.mvvmcleanarchitecturedemo.domain.usecase

import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.domain.repository.NewsRepository

class SaveNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(article: Article) {
        repository.saveNews(article)
    }
}