package com.kkcoding.data.datasource.local

import com.kkcoding.data.db.entities.ArticleItemEntity
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDatasource {


    suspend fun saveArticle(article:ArticleItemEntity)

    fun getAllSaveArticles():Flow<List<ArticleItemEntity>>

    suspend fun unsaveArticle(article: ArticleItemEntity)

}