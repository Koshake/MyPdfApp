package com.example.mypdfapp.di

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object DI {

    val app: AppComponentHolder by lazy {
        AppComponentHolder(context)
    }

    private lateinit var context: Context

    fun init(context: Context) {
        DI.context = context
    }
}