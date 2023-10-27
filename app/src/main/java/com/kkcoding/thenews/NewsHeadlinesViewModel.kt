package com.kkcoding.thenews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcoding.domain.repository.ArticleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsHeadlinesViewModel @Inject constructor (private val repo: ArticleRepo):ViewModel() {

    init {

    }

    fun getHeadlineArticles() {
        viewModelScope.launch {
            val list = repo.getHeadLineArticles()
            Log.d("articleRes", ": $list")
        }
    }
}