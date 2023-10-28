package com.kkcoding.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkcoding.data.db.entities.ArticleItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article:ArticleItemEntity)

    @Query("select * from ArticleItemEntity")
    fun getAllArticles(): Flow<List<ArticleItemEntity>>

    @Delete
    fun removeSavedArticle(article: ArticleItemEntity)

}