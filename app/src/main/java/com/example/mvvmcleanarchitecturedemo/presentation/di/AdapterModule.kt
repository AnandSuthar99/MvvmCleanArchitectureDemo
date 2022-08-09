package com.example.mvvmcleanarchitecturedemo.presentation.di

import com.example.mvvmcleanarchitecturedemo.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AdapterModule {

    @Provides
    fun providesNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }
}