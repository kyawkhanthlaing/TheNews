package com.kkcoding.domain.repository

import com.kkcoding.data.ArticleApi
import com.kkcoding.data.datasource.ArticleRemoteDatasource
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.domain.model.mapToDomain
import javax.inject.Inject

class ArticleRepoImpl @Inject constructor(private val datasource: ArticleRemoteDatasource):ArticleRepo {
    override suspend fun getHeadLineArticles(): List<ArticleItem> {

        val result = datasource.getHeadlineArticles().map { it.mapToDomain() }
        return result
    }
}