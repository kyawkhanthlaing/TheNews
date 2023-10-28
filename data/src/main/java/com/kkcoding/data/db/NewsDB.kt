package com.kkcoding.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kkcoding.data.db.dao.ArticleDAO
import com.kkcoding.data.db.entities.ArticleItemEntity

@Database(entities = [ArticleItemEntity::class], version = 2)
abstract class NewsDB : RoomDatabase() {
    abstract fun articleDAO(): ArticleDAO
}