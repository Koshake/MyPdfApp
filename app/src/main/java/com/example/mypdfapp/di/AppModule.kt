package com.example.mypdfapp.di

import android.content.Context
import com.example.mypdfapp.datasource.PdfBitmapDataSource
import com.example.mypdfapp.datasource.PdfBitmapDataSourceImpl
import com.example.mypdfapp.download.DownloadFileManager
import com.example.mypdfapp.download.DownloadFileManagerImpl
import com.example.mypdfapp.paging.PagingRepository
import com.example.mypdfapp.paging.PagingRepositoryImpl
import com.example.mypdfapp.repository.PdfRepositoryImpl
import com.example.mypdfapp.repository.PdfViewRepository
import com.example.mypdfapp.ui.MainFragment
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @AppScope
    internal fun providePdfViewRepository(dataSource: PdfBitmapDataSource): PdfViewRepository {
        return PdfRepositoryImpl(dataSource)
    }

    @Provides
    @AppScope
    internal fun providePagingRepository(repository: PdfViewRepository): PagingRepository {
        return PagingRepositoryImpl(repository)
    }

    @Provides
    @AppScope
    internal fun providePdfDataSource(downloadFileManager: DownloadFileManager): PdfBitmapDataSource {
        return PdfBitmapDataSourceImpl(downloadFileManager)
    }

    @Provides
    @AppScope
    internal fun provideDownloadFileManager(context: Context): DownloadFileManager {
        return DownloadFileManagerImpl(context)
    }

}
