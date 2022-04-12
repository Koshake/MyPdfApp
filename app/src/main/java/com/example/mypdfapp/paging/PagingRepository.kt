package com.example.mypdfapp.paging

import android.graphics.Bitmap
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mypdfapp.paging.PagingSource.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

interface PagingRepository {
   fun createDataFlow(fileName: String, width: Int,pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Bitmap>>

   private fun getDefaultPageConfig(): PagingConfig {
      return PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = true)
   }
}