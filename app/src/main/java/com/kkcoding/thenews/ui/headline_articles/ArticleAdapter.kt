package com.kkcoding.thenews.ui.headline_articles


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.thenews.R
import com.kkcoding.thenews.databinding.ItemArticleNewsBinding

class ArticleAdapter(
    val onClickItem: (ArticleItem) -> Unit,
    val onSaveArticle: (ArticleItem) -> Unit,
    val onUnsaveArticle: (ArticleItem) -> Unit
) :
    ListAdapter<ArticleItem, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    inner class ArticleViewHolder(val binding: ItemArticleNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.binding.apply {
            Glide.with(ivArticleImage).load(article.urlToImage).into(ivArticleImage)
            tvTitle.text = article.title
            tvPublishedTime.text = article.publishedAt
            ivSaved.setImageResource(if (article.isSaved) R.drawable.ic_saved else R.drawable.ic_unsaved)
            root.setOnClickListener {
                onClickItem(article)
            }
            ivSaved.setOnClickListener {
                if (article.isSaved) onUnsaveArticle(article) else onSaveArticle(article)
            }
        }

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