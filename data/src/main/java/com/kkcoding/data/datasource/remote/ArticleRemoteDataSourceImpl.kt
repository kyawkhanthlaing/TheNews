package com.kkcoding.data.datasource.remote

import com.kkcoding.data.api.ArticleApi
import com.kkcoding.data.response.article.ArticleDataResponse
import com.kkcoding.data.util.GenericResponse
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(private val api: ArticleApi) :
    ArticleRemoteDatasource() {
    override suspend fun getHeadlineArticles(category:String): GenericResponse<ArticleDataResponse> {
        return safeApiCall { api.getTopHeadlines(category = category) }
    }
}