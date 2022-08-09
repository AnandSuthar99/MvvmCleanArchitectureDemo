package com.example.mvvmcleanarchitecturedemo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mvvmcleanarchitecturedemo.R
import com.example.mvvmcleanarchitecturedemo.databinding.ActivityMainBinding
import com.example.mvvmcleanarchitecturedemo.presentation.viewmodel.NewsViewModel
import com.example.mvvmcleanarchitecturedemo.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bnvNews.setupWithNavController(
            navHostFragment.findNavController()
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }
}