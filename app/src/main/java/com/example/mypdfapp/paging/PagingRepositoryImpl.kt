package com.example.mypdfapp.paging

import android.graphics.Bitmap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mypdfapp.repository.PdfViewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingRepositoryImpl @Inject constructor(private val pdfRepository: PdfViewRepository) : PagingRepository {

    override fun createDataFlow(fileName: String, width: Int, pagingConfig: PagingConfig): Flow<PagingData<Bitmap>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PagingSource(pdfRepository, fileName, width) }
        ).flow
    }

}