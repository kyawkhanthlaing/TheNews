package com.kkcoding.data.datasource

import android.util.Log
import com.kkcoding.data.ArticleApi
import com.kkcoding.data.response.ArticleItemResponse
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(private val api:ArticleApi):ArticleRemoteDatasource {
    override suspend fun getHeadlineArticles(): List<ArticleItemResponse> {

       // return try {
           val result = api.getTopHeadlines()
        Log.d("apiResult", "getHeadlineArticles: ${result.code()} ${result.errorBody().toString()}")
          return result.body()?.articles ?: emptyList()
//        }catch (e:Exception) {
//            e.printStackTrace()
//            emptyList()
//        }

    }
}