package com.example.mvvmcleanarchitecturedemo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.mvvmcleanarchitecturedemo.R
import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.databinding.FragmentInfoBinding
import com.example.mvvmcleanarchitecturedemo.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var article: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        article = InfoFragmentArgs.fromBundle(requireArguments()).selectedArticle
        viewModel = (activity as MainActivity).viewModel

        fragmentInfoBinding.wvNewsInfo.apply {
            webViewClient = WebViewClient()
            if (!article.url.isNullOrBlank()) loadUrl(article.url!!)
        }

        fragmentInfoBinding.fabSaveArticle.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Saved successfully.", Snackbar.LENGTH_LONG).show()
        }
    }
}