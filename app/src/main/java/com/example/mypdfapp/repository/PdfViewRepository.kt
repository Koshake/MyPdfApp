package com.example.mypdfapp.repository

import android.graphics.Bitmap
import java.io.File

interface PdfViewRepository {

    suspend fun downloadPdf(fileUri: String) : String

    suspend fun getPdfFromStorage(fileUri: String) : File

    suspend fun renderSinglePage(filePath: String, pageNumber: Int, width: Int) : Bitmap

    suspend fun renderAllPages(filePath: String, firstPage: Int, lastPage: Int, width: Int) : List<Bitmap>

    fun getPagesCount(filePath: String) : Int

}