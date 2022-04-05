package com.example.mypdfapp

import android.app.Application
import com.example.mypdfapp.di.DI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.init(context = applicationContext)
    }
}