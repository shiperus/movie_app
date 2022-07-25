package com.tkpd.movieapp.feature.movielist

import android.util.Log

class ImportantClass {
    init {
        for (i in 1..1000000000) {

        }
    }

    fun testLog() {
        val className = ImportantClass::class.java.name
        Log.i(className, "testLog: $className")
    }
}