package com.example.mypdfapp.di

import android.content.Context
import com.example.mypdfapp.viewmodel.MainViewModel
import com.example.mypdfapp.viewmodel.PdfViewModel
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [
    AppModule::class,
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): AppComponent
    }

    fun inject(entry: PdfViewModel.Factory)

    fun inject(entry: MainViewModel.Factory)

}
