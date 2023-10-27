package com.kkcoding.data

import com.kkcoding.data.datasource.ArticleRemoteDataSourceImpl
import com.kkcoding.data.datasource.ArticleRemoteDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticleDataModule {

    @Provides
    fun provideOkhttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideArticleApi(retrofit: Retrofit):ArticleApi = retrofit.create(ArticleApi::class.java)



}

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindArticleDatasource(
        articleRepoImpl: ArticleRemoteDataSourceImpl
    ): ArticleRemoteDatasource
}
