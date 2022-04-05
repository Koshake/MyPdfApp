package com.example.mypdfapp.di

import android.content.Context
import com.example.mypdfapp.download.DownloadFileManager
import com.example.mypdfapp.download.DownloadFileManagerImpl
import com.example.mypdfapp.repository.PdfRepositoryImpl
import com.example.mypdfapp.repository.PdfViewRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @AppScope
    internal fun providePdfViewRepository(downloadFileManager: DownloadFileManager): PdfViewRepository {
        return PdfRepositoryImpl(downloadFileManager)
    }

    @Provides
    @AppScope
    internal fun provideDownloadFileManager(context: Context): DownloadFileManager {
        return DownloadFileManagerImpl(context)
    }

}
