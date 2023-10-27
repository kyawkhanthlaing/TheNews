package com.kkcoding.domain

import com.kkcoding.domain.repository.ArticleRepo
import com.kkcoding.domain.repository.ArticleRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleDomainModule {
    @Binds
    @Singleton
    abstract fun bindArticlesRepository(
        articleRepoImpl: ArticleRepoImpl
    ): ArticleRepo
}