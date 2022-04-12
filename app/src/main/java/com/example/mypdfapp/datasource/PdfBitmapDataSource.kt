package com.example.mypdfapp.datasource

import android.graphics.Bitmap
import kotlinx.coroutines.Deferred
import java.util.Queue

interface PdfBitmapDataSource {

    suspend fun getPages(filePath: String, firstPage: Int, lastPage: Int, width: Int) : Deferred<List<Bitmap>>

    suspend fun downloadPdf(fileUri: String): String

    fun getPagesCount(filePath: String) : Int
}