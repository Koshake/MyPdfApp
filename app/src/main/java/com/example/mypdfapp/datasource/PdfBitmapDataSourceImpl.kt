package com.example.mypdfapp.datasource

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import com.example.mypdfapp.download.DownloadFileManager
import com.example.mypdfapp.extensions.renderBitmap
import com.example.mypdfapp.paging.PagingSource.Companion.INITIAL_PAGE_SIZE
import com.example.mypdfapp.paging.PagingSource.Companion.PAGE_SIZE
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File

class PdfBitmapDataSourceImpl(private val dataManager: DownloadFileManager) : PdfBitmapDataSource {

    override suspend fun getPages(filePath: String, firstPage: Int, lastPage: Int, width: Int): Deferred<List<Bitmap>> {
        val list = arrayListOf<Bitmap>()
        return withContext(Dispatchers.IO) {
            async {
                PdfRenderer(ParcelFileDescriptor.open(File(filePath), ParcelFileDescriptor.MODE_READ_ONLY)).use { renderer ->
                    for (i in firstPage until lastPage.coerceAtMost(renderer.pageCount - 1)) {
                        list.add(renderer.openPage(i).renderBitmap(width))
                    }
                    list
                }
            }
        }
    }

    override fun getPagesCount(filePath: String) =
        PdfRenderer(ParcelFileDescriptor.open(File(filePath), ParcelFileDescriptor.MODE_READ_ONLY)).pageCount

    override suspend fun downloadPdf(fileUri: String): String {
        return dataManager.download(
            fileUri = fileUri,
            fileName = fileUri.substring(fileUri.lastIndexOf('/') + 1)
        )
    }

}