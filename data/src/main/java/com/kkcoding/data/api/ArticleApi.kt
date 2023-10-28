package com.kkcoding.data.api

import com.kkcoding.data.response.article.ArticleDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query(value = "country") country:String = "us",
        @Query(value = "category") category:String = "",
    ): Response<ArticleDataResponse>

}





