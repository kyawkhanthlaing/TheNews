package com.kkcoding.data

import android.content.Context
import androidx.room.Room
import com.kkcoding.data.api.ArticleApi
import com.kkcoding.data.api.HeaderInterceptor
import com.kkcoding.data.datasource.local.ArticleLocalDatasource
import com.kkcoding.data.datasource.local.ArticleLocalDatasourceImpl
import com.kkcoding.data.datasource.remote.ArticleRemoteDataSourceImpl
import com.kkcoding.data.datasource.remote.ArticleRemoteDatasource
import com.kkcoding.data.db.NewsDB
import com.kkcoding.data.db.dao.ArticleDAO
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticleDataModule {

    @Provides
    fun provideApiKey():String = BuildConfig.API_KEY


    @Provides
    fun provideOkhttp(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Provides
    fun provideArticleApi(retrofit: Retrofit): ArticleApi = retrofit.create(ArticleApi::class.java)


    @Provides
    fun provideInterceptor(api_key:String) = HeaderInterceptor(api_key)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NewsDB {
        return Room.databaseBuilder(
            appContext,
            NewsDB::class.java,
            "NewsDB"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideArticleDao(appDatabase: NewsDB): ArticleDAO {
        return appDatabase.articleDAO()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindArticleRemoteDatasource(
        articleRemoteImpl: ArticleRemoteDataSourceImpl
    ): ArticleRemoteDatasource


    @Binds
    @Singleton
    abstract fun bindArticleLocalDatasource(
        articleLocalImpl: ArticleLocalDatasourceImpl
    ): ArticleLocalDatasource
}
