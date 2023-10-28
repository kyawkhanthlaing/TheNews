package com.kkcoding.data

import com.kkcoding.data.response.article.ArticleDataResponse
import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Named

interface ArticleApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
     //   @Query(value = "apiKey") apiKey: String = BuildConfig.API_KEY ,
        @Query(value = "country") country:String = "us",
        @Query(value = "category") category:String = "",
    ): Response<ArticleDataResponse>

}

class HeaderInterceptor(private val key:String):Interceptor {

//    @Inject
//    @Named("api_key")
//    lateinit var apiKey: String
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request  = chain.request()
           val new = request
            .newBuilder()
            .addHeader("X-Api-Key",key)
            .build()

        return chain.proceed(new)
    }

}



