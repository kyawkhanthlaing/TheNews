package com.kkcoding.data

import com.kkcoding.data.response.ArticleDataResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query(value = "apiKey") apiKey: String = BuildConfig.API_KEY ,
        @Query(value = "country") country:String = "us"
    ): Response<ArticleDataResponse>

}



