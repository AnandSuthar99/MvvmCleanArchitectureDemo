package com.example.mvvmcleanarchitecturedemo.presentation.di

import com.example.mvvmcleanarchitecturedemo.data.api.NewsApiService
import com.example.mvvmcleanarchitecturedemo.data.repository.datasource.NewsRemoteDataSource
import com.example.mvvmcleanarchitecturedemo.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun providesNewsRemoteDataSource(newsApiService: NewsApiService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsApiService)
    }
}