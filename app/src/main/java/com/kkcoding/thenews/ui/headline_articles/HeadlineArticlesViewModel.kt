package com.kkcoding.thenews.ui.headline_articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcoding.core.exts.unknownErrorOnNull
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.domain.repository.ArticleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlineArticlesViewModel @Inject constructor(private val repo: ArticleRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(HeadlinesArticleUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow(HeadlineArticleCategory.US)

    val selectedCategory get() = _selectedCategory.asStateFlow()

    init {
        getHeadlineArticlesByCategory(selectedCategory.value.code)
    }

    private fun getHeadlineArticlesByCategory(code: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            repo.getHeadLineArticles(code).combine(repo.getSavedArticles()) { remote, local ->
                Pair(remote, local)
            }.collectLatest { pair ->
                pair.first.onFailure { error ->
                    _uiState.update {
                        it.copy(errorMsg = error.message.unknownErrorOnNull(), loading = false)
                    }
                }.onSuccess { articles ->

                    val checkedStatusList =
                        articles.map { item ->
                            if (pair.second.any { it.url == item.url })
                                item.copy(isSaved = true)
                            else item
                        }

                    _uiState.update {
                        it.copy(errorMsg = null, articles = checkedStatusList, loading = false)
                    }
                }
            }
        }
    }

    fun saveArticle(articleItem: ArticleItem) {
        viewModelScope.launch {
            repo.saveArticle(articleItem)
        }
        updateSaveStatus(articleItem, true)
    }


    private fun updateSaveStatus(item: ArticleItem, isSave: Boolean) {
        _uiState.update {
            it.copy(
                articles = it.articles?.map { article ->
                    if (article == item)
                        article.copy(isSaved = isSave)
                    else article
                }
            )
        }
    }

    fun unsaveArticle(articleItem: ArticleItem) {
        viewModelScope.launch {
            repo.unsaveArticle(articleItem)
        }
        updateSaveStatus(articleItem, false)
    }

    fun updateCategory(category: HeadlineArticleCategory) {

        if (category != selectedCategory.value) {
            getHeadlineArticlesByCategory(category.code)
        }

        _selectedCategory.update { category }
    }
}

data class HeadlinesArticleUiState(
    val articles: List<ArticleItem>? = null,
    val loading: Boolean = false,
    val errorMsg: String? = null
)

enum class HeadlineArticleCategory(val title: String, val code: String) {
    US("US", ""),
    BUSINESS("Business", "business"),
    ENTERTAINMENT(
        "Entertainment",
        "entertainment"
    ),
    HEALTH("Health", "health"), SCIENCE("Science", "science"),
    TECHNOLOGY("Technology", "technology"), SPORTS("Sports", "sports")
}