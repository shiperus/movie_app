package com.tkpd.movieapp

import android.app.Application
import com.tkpd.movieapp.util.RetrofitInstanceBuilder
import okhttp3.Cache

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(applicationContext.cacheDir, cacheSize)
        RetrofitInstanceBuilder.cache = myCache
    }
}