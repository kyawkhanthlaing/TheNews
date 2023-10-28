package com.kkcoding.domain.repository

import com.kkcoding.domain.model.ArticleItem
import kotlinx.coroutines.flow.Flow

interface ArticleRepo {
    suspend fun getHeadLineArticles(category:String): Flow<Result<List<ArticleItem>>>

    suspend fun saveArticle(articleItem: ArticleItem)

    suspend fun unsaveArticle(articleItem: ArticleItem)

    suspend fun getSavedArticles():Flow<List<ArticleItem>>

}