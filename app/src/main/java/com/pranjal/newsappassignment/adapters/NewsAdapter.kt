package com.pranjal.newsappassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pranjal.newsappassignment.databinding.NewsLayoutAdapterBinding
import com.pranjal.newsappassignment.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private var binding: NewsLayoutAdapterBinding? = null

    inner class ArticleViewHolder(itemBinding: NewsLayoutAdapterBinding ) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        binding= NewsLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false)

       return ArticleViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                    .load(currentArticle.urlToImage)
                    .into(binding?.ivArticleImage!!)

            binding?.tvDescription?.text= currentArticle.description
            binding?.tvTitle?.text= currentArticle.title
            binding?.tvPublishedAt?.text =currentArticle.publishedAt
            binding?.tvSource?.text= currentArticle.source?.name

            setOnClickListener {
                onItemClickListener ?. let {it(currentArticle) }
            }
        }
    }
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener

    }
}