package com.kkcoding.thenews


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.thenews.databinding.ItemArticleNewsBinding

class ArticleAdapter: ListAdapter<ArticleItem, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    inner class ArticleViewHolder(val binding: ItemArticleNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=getItem(position)
        holder.binding.apply {
            Glide.with(ivArticleImage).load(article.urlToImage).into(ivArticleImage)
            tvTitle.text = article.title
            tvPublishedTime.text = article.publishedAt
            root.setOnClickListener{
                onItemClickListener?.let { it -> it(article) }
            }
        }

    }

    private var onItemClickListener: ((ArticleItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticleItem) -> Unit) {
        onItemClickListener = listener
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<ArticleItem>() {
    override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
        return oldItem == newItem
    }
}