package com.kkcoding.data.api

import okhttp3.Interceptor

class HeaderInterceptor(private val key:String): Interceptor {

//    @Inject
//    @Named("api_key")
//    lateinit var apiKey: String
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request  = chain.request()
           val new = request
            .newBuilder()
            .addHeader("X-Api-Key",key)
            .build()

        return chain.proceed(new)
    }

}