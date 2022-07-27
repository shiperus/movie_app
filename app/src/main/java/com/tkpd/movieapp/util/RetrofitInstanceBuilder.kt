package com.tkpd.movieapp.util

import com.tkpd.movieapp.constant.MovieConstant
import com.tkpd.movieapp.datasource.MovieAPI
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Yehezkiel on 10/05/20
 */
object RetrofitInstanceBuilder {
    const val CACHE_CONTROL_HEADER = "Cache-Control"
    const val CACHE_CONTROL_NO_CACHE = "no-cache"

    var cache : Cache? = null
    val RETROFIT_INSTANCE: MovieAPI by lazy {
        Retrofit.Builder()
            .baseUrl(MovieConstant.MOVIE_BASE_URL)
            .client(okHttpClientInstance)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieAPI::class.java)
    }

    private val okHttpClientInstance: OkHttpClient by lazy {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.addInterceptor(cacheLogicInterceptor)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(requestInterceptor)
        client.build()
    }

    private val requestInterceptor : Interceptor by lazy {
        RequestInterceptor()
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    private val cacheLogicInterceptor: Interceptor by lazy {
        val interceptor = object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val originalResponse = chain.proceed(request)

                val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
                if (!shouldUseCache) return originalResponse

                val cacheControl = CacheControl.Builder()
                    .maxAge(30, TimeUnit.DAYS)
                    .build()

                return originalResponse.newBuilder()
                    .header(CACHE_CONTROL_HEADER, cacheControl.toString())
                    .build()
            }
        }
        interceptor
    }
}