package com.example.mypdfapp.repository

import android.graphics.Bitmap
import com.example.mypdfapp.datasource.PdfBitmapDataSource
import java.io.File

class PdfRepositoryImpl(
    private val dataSource: PdfBitmapDataSource,
) : PdfViewRepository {

    override suspend fun downloadPdf(fileUri: String): String {
        return dataSource.downloadPdf(fileUri)
    }

    override suspend fun getPdfFromStorage(fileUri: String): File {
        return File(fileUri)
    }

    override suspend fun renderAllPages(filePath: String, firstPage: Int, lastPage: Int, width: Int): List<Bitmap> {
        return dataSource.getPages(filePath, firstPage, lastPage, width).await()
    }

    override fun getPagesCount(filePath: String) =
        dataSource.getPagesCount(filePath)
}