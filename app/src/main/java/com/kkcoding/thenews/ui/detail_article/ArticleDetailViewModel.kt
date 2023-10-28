package com.kkcoding.thenews.ui.detail_article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.domain.repository.ArticleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(private val repo: ArticleRepo):ViewModel() {
    fun saveArticle(articleItem: ArticleItem) {
        viewModelScope.launch {
            repo.saveArticle(articleItem)
        }
    }

    fun unsaveArticle(articleItem: ArticleItem) {
        viewModelScope.launch {
            repo.unsaveArticle(articleItem)
        }
    }
}