package com.example.mvvmcleanarchitecturedemo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmcleanarchitecturedemo.R
import com.example.mvvmcleanarchitecturedemo.data.util.Resource
import com.example.mvvmcleanarchitecturedemo.databinding.FragmentNewsBinding
import com.example.mvvmcleanarchitecturedemo.presentation.adapter.NewsAdapter
import com.example.mvvmcleanarchitecturedemo.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        newsViewModel = (activity as MainActivity).viewModel

        hideProgressBar()

        initRecyclerView()
        initSearchView()
        showNews()
    }

    private fun initRecyclerView() {
        fragmentNewsBinding.apply {
            rvNewsHeadlines.adapter = newsAdapter
            newsAdapter.setOnItemClickListener {
                Navigation.findNavController(root)
                    .navigate(NewsFragmentDirections.actionNewsFragmentToInfoFragment(it))
            }
            rvNewsHeadlines.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initSearchView() {
        fragmentNewsBinding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                newsViewModel.getSearchedNewsHeadlines("us", query.toString(), 1)
                showSearchedNews()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(1000)
                    newsViewModel.getSearchedNewsHeadlines("us", newText.toString(), 1)
                    showSearchedNews()
                }
                return false
            }
        })

        fragmentNewsBinding.svNews.setOnCloseListener {
            initRecyclerView()
            showNews()
            false
        }
    }

    private fun showSearchedNews() {
        if (view == null) return
        newsViewModel.searchedNewsHeadlines.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(context, "Received an error: ${it.msg}", Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    newsAdapter.differ.submitList(it.data?.articles?.toList())
                }
            }
        }
    }

    private fun showNews() {
        newsViewModel.getNewsHeadlines("us", 1)
        newsViewModel.newsHeadlines.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(context, "Received an error: ${it.msg}", Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    newsAdapter.differ.submitList(it.data?.articles?.toList())
                }
            }
        }
    }

    private fun showProgressBar() {
        fragmentNewsBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        fragmentNewsBinding.pbLoading.visibility = View.INVISIBLE
    }
}