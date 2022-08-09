package com.example.mvvmcleanarchitecturedemo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmcleanarchitecturedemo.R
import com.example.mvvmcleanarchitecturedemo.databinding.FragmentSavedBinding
import com.example.mvvmcleanarchitecturedemo.presentation.adapter.NewsAdapter
import com.example.mvvmcleanarchitecturedemo.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment : Fragment() {

    @Inject
    lateinit var adapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    private lateinit var fragmentSavedBinding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSavedBinding = FragmentSavedBinding.bind(view)
        newsViewModel = (activity as MainActivity).viewModel
        initRecyclerView(view)
        showNews()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapter.differ.currentList[position]
                newsViewModel.deleteSavedNews(article)
                Snackbar.make(view, "Deleted successfully.", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        newsViewModel.saveArticle(article)
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(fragmentSavedBinding.recyclerView)
        }
    }

    private fun initRecyclerView(view: View) {
        fragmentSavedBinding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        }
    }

    private fun showNews() {
        newsViewModel.getSavedNews().observe(viewLifecycleOwner) {
            adapter.differ.submitList(it.toList())
        }
    }

}