package com.example.mypdfapp.di

import android.content.Context

class AppComponentHolder(private val context: Context) : RootComponentHolder<AppComponent>() {

    override fun provideInternal(): AppComponent {
        return DaggerAppComponent
                .builder()
                .appContext(context)
                .build()
    }

    override fun onComponentDestroyed() {
        super.onComponentDestroyed()
    }
}

