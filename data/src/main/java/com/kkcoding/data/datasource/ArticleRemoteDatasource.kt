package com.kkcoding.data.datasource

import com.kkcoding.data.response.ArticleItemResponse

interface ArticleRemoteDatasource {

    suspend fun getHeadlineArticles():List<ArticleItemResponse>

}