package com.kkcoding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleItemEntity(
    val id:Int = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String
)