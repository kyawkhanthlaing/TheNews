package com.kkcoding.domain.repository

import com.kkcoding.core.exts.unknownErrorOnNull
import com.kkcoding.data.datasource.local.ArticleLocalDatasourceImpl
import com.kkcoding.data.datasource.remote.ArticleRemoteDatasource
import com.kkcoding.data.util.GenericResponse
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.domain.model.mapToDomain
import com.kkcoding.domain.model.mapToEntity
import com.kkcoding.domain.util.NewsException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepoImpl @Inject constructor(
    private val remoteDS: ArticleRemoteDatasource,
    private val localDS: ArticleLocalDatasourceImpl
) :
    ArticleRepo {
    override suspend fun getHeadLineArticles(category: String): Flow<Result<List<ArticleItem>>> {

        return flow {

            when (val result = remoteDS.getHeadlineArticles(category)) {
                is GenericResponse.Error -> {
                    emit(Result.failure(NewsException(result.message.unknownErrorOnNull())))
                }

                is GenericResponse.Success -> {
                    val mappedResult =
                        result.data?.articles?.map { it.mapToDomain() } ?: emptyList()
                    emit(Result.success(mappedResult))
                }
            }
        }
    }

    override suspend fun saveArticle(articleItem: ArticleItem) {
        withContext(Dispatchers.IO) {
            localDS.saveArticle(articleItem.mapToEntity())
        }
    }

    override suspend fun unsaveArticle(articleItem: ArticleItem) {
        withContext(Dispatchers.IO) {
            localDS.unsaveArticle(articleItem.mapToEntity())
        }
    }

    override suspend fun getSavedArticles(): Flow<List<ArticleItem>> {
        return localDS.getAllSaveArticles().map { list -> list.map { it.mapToDomain() } }
    }
}

