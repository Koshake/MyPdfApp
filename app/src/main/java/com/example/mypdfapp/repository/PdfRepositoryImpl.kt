package com.example.mypdfapp.repository

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import com.example.mypdfapp.download.DownloadFileManager
import com.example.mypdfapp.extensions.renderBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.min

class PdfRepositoryImpl(private val dataManager: DownloadFileManager) : PdfViewRepository {

    private val list = mutableListOf<Bitmap>()

    override suspend fun downloadPdf(fileUri: String): String {
        return dataManager.download(
            fileUri = fileUri,
            fileName = fileUri.substring(fileUri.lastIndexOf('/') + 1)
        )
    }

    override suspend fun getPdfFromStorage(fileUri: String): File {
        return File(fileUri)
    }

    override suspend fun renderSinglePage(filePath: String, pageNumber : Int, width: Int): Bitmap {
        val bitmap = withContext(Dispatchers.IO) {
            async {
                PdfRenderer(ParcelFileDescriptor.open(File(filePath), ParcelFileDescriptor.MODE_READ_ONLY)).use { renderer ->
                    renderer.openPage(pageNumber).renderBitmap(width)
                }
            }
        }
        return bitmap.await()
    }

    override suspend fun renderAllPages(filePath: String, firstPage: Int, lastPage: Int, width: Int): List<Bitmap> {
        list.clear()
        val bitmap = withContext(Dispatchers.IO) {
            async {
                PdfRenderer(ParcelFileDescriptor.open(File(filePath), ParcelFileDescriptor.MODE_READ_ONLY)).use { renderer ->
                    for (i in firstPage until lastPage) {
                        list.add(renderer.openPage(i).renderBitmap(width))
                    }
                    list
                }
            }
        }
        return bitmap.await()
    }

    override fun getPagesCount(filePath: String) =
        PdfRenderer(ParcelFileDescriptor.open(File(filePath), ParcelFileDescriptor.MODE_READ_ONLY)).pageCount
}