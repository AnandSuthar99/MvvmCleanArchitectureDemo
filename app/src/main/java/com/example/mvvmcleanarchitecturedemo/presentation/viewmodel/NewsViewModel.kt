package com.example.mvvmcleanarchitecturedemo.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmcleanarchitecturedemo.data.model.APIResponse
import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.data.util.Resource
import com.example.mvvmcleanarchitecturedemo.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
) : AndroidViewModel(app) {

    val newsHeadlines = MutableLiveData<Resource<APIResponse>>()
    val searchedNewsHeadlines = MutableLiveData<Resource<APIResponse>>()

    fun getNewsHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                newsHeadlines.postValue(apiResult)
            } else {
                newsHeadlines.postValue(Resource.Error(msg = "Internet not available."))
            }
        } catch (e: Exception) {
            newsHeadlines.postValue(Resource.Error(msg = e.message!!))
            e.printStackTrace()
        }
    }


    fun getSearchedNewsHeadlines(country: String, query: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            searchedNewsHeadlines.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getSearchedNewsUseCase.execute(country, query, page)
                    searchedNewsHeadlines.postValue(apiResult)
                } else {
                    searchedNewsHeadlines.postValue(Resource.Error(msg = "Internet not available."))
                }
            } catch (e: Exception) {
                searchedNewsHeadlines.postValue(Resource.Error(msg = e.message!!))
                e.printStackTrace()
            }
        }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            saveNewsUseCase.execute(article)
        }
    }

    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteSavedNews(article: Article) {
        viewModelScope.launch {
            deleteSavedNewsUseCase.execute(article)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            if (connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
            ) {
                return true
            }
        }
        return false
    }
}