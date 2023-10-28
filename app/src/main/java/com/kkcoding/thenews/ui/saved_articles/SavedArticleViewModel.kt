package com.kkcoding.thenews.ui.saved_articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.domain.repository.ArticleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedArticleViewModel @Inject constructor(private val repo:ArticleRepo):ViewModel() {


    private val _uiState:MutableStateFlow<List<ArticleItem>> = MutableStateFlow(emptyList())
    val uiState get() = _uiState.asStateFlow()

    init {
        getSavedArticles()
    }

    private fun getSavedArticles() {
        viewModelScope.launch {
            repo.getSavedArticles().collectLatest { result ->
                _uiState.update {
                    result
                }
            }
        }
    }


    fun unsaveArticle(articleItem: ArticleItem) {
        viewModelScope.launch {
            repo.unsaveArticle(articleItem)
        }
    }
}