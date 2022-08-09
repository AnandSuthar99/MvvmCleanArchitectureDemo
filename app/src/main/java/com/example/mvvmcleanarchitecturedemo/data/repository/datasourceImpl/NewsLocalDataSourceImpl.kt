package com.example.mvvmcleanarchitecturedemo.data.repository.datasourceImpl

import com.example.mvvmcleanarchitecturedemo.data.db.ArticleDAO
import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getSavedNews()
    }

    override suspend fun deleteArticle(article: Article): Int {
        return articleDAO.deleteArticle(article)
    }
}