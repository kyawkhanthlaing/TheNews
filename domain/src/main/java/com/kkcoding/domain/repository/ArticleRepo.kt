package com.kkcoding.domain.repository

import com.kkcoding.domain.model.ArticleItem

interface ArticleRepo {

    suspend fun getHeadLineArticles():List<ArticleItem>
}