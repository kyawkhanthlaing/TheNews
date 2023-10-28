package com.kkcoding.data.datasource.local

import com.kkcoding.data.db.dao.ArticleDAO
import com.kkcoding.data.db.entities.ArticleItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleLocalDatasourceImpl @Inject constructor(private val dao: ArticleDAO):ArticleLocalDatasource {
    override suspend fun saveArticle(article: ArticleItemEntity) {
        dao.insertArticle(article)
    }

    override fun getAllSaveArticles(): Flow<List<ArticleItemEntity>> {
        return dao.getAllArticles()
    }

    override suspend fun unsaveArticle(article: ArticleItemEntity) {
        return dao.removeSavedArticle(article)
    }


}