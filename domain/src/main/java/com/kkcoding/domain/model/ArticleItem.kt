package com.kkcoding.domain.model

import com.kkcoding.data.response.ArticleItemResponse

data class ArticleItem(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)


fun ArticleItemResponse.mapToDomain()  = ArticleItem(
    author.orEmpty(), content.orEmpty(), description.orEmpty(), publishedAt.orEmpty(), title.orEmpty(), url.orEmpty(), urlToImage.orEmpty()
)