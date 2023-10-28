package com.kkcoding.data.datasource.remote

import com.kkcoding.core.exts.unknownErrorOnNull
import com.kkcoding.data.response.article.ArticleDataResponse
import com.kkcoding.data.response.error.NewsApiErrorResponse
import com.kkcoding.data.util.GenericResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class ArticleRemoteDatasource {

    abstract suspend fun getHeadlineArticles(category: String): GenericResponse<ArticleDataResponse>

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): GenericResponse<T> {

        return withContext(Dispatchers.IO) {
            try {

                val response: Response<T> = apiCall()
                if (response.isSuccessful) {
                    GenericResponse.Success(data = response.body()!!)
                } else {
                    val errorResponse: NewsApiErrorResponse? =
                        response.errorBody().convertErrorBody()
                    GenericResponse.Error(
                        errorMessage = errorResponse?.message ?: "Something went wrong"
                    )
                }

            } catch (e: HttpException) {
                GenericResponse.Error(errorMessage = e.message.unknownErrorOnNull())
            } catch (e: IOException) {
                GenericResponse.Error("Please check your network connection")
            } catch (e: Exception) {
                GenericResponse.Error(errorMessage = "Something went wrong!")
            }
        }
    }

    private fun ResponseBody?.convertErrorBody(): NewsApiErrorResponse? {
        return try {
            this?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(NewsApiErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

}





