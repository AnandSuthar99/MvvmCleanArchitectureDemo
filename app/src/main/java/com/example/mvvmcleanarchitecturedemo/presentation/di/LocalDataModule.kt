package com.example.mvvmcleanarchitecturedemo.presentation.di

import com.example.mvvmcleanarchitecturedemo.data.db.ArticleDAO
import com.example.mvvmcleanarchitecturedemo.data.repository.datasource.NewsLocalDataSource
import com.example.mvvmcleanarchitecturedemo.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }
}