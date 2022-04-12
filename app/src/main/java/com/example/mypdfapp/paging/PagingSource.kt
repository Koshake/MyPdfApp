package com.example.mypdfapp.paging

import android.graphics.Bitmap
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypdfapp.repository.PdfRepositoryImpl
import com.example.mypdfapp.repository.PdfViewRepository
import java.io.IOException

class PagingSource(
    private val repository: PdfViewRepository,
    private val fileName: String,
    private val width: Int
    ) : PagingSource<Int, Bitmap>() {

    companion object {
        private const val FIRST_PAGE = 0
        const val PAGE_SIZE = 16
        const val INITIAL_PAGE_SIZE = 32
    }

    override fun getRefreshKey(state: PagingState<Int, Bitmap>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bitmap> {
        return try {
            if (fileName.isEmpty()) {
                LoadResult.Page(
                    emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                val page = params.key ?: FIRST_PAGE
                val nextPage = page + params.loadSize

                val result = repository.renderAllPages(
                    filePath = fileName,
                    firstPage = page,
                    lastPage = nextPage,
                    width = width)
                val prev = if (page == 0) null else page - 1
                val next = if (result.size < params.loadSize) null else nextPage
                Log.d(
                    "Tag", "PageSource load first = $page second = $nextPage result_size = ${result.size}" +
                            " load = ${params.loadSize}")
                LoadResult.Page(
                    data = result,
                    prevKey = prev,
                    nextKey = next
                )
            }
        } catch (exception : IOException) {
            return  LoadResult.Error(exception)
        }

    }
}