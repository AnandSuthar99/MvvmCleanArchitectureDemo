package com.example.mvvmcleanarchitecturedemo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmcleanarchitecturedemo.R
import com.example.mvvmcleanarchitecturedemo.data.model.Article
import com.example.mvvmcleanarchitecturedemo.databinding.RowNewsItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

    inner class NewsItemViewHolder(private val binding: RowNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                this.tvTitle.text = article.title
                this.tvDescription.text = article.description
                this.tvSource.text = article.source?.name
                this.tvPublishedAt.text =
                    "${article.getFormattedPublishedAt()} - ${article.source?.name}"
                Glide.with(this.ivArticleImage.context).load(article.urlToImage)
                    .error(R.drawable.ic_image_not_supported_24)
                    .into(this.ivArticleImage)

                this.root.setOnClickListener {
                    onItemClickListener?.let { it1 -> it1(article) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            RowNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }
}