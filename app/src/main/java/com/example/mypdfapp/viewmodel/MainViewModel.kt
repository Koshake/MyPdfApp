package com.example.mypdfapp.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypdfapp.di.DI
import com.example.mypdfapp.model.viewstate.PdfReaderViewState
import com.example.mypdfapp.paging.PagingRepository
import com.example.mypdfapp.paging.PagingRepositoryImpl
import com.example.mypdfapp.paging.PagingSource
import com.example.mypdfapp.paging.PagingSource.Companion.INITIAL_PAGE_SIZE
import com.example.mypdfapp.paging.PagingSource.Companion.PAGE_SIZE
import com.example.mypdfapp.repository.PdfViewRepository
import com.example.mypdfapp.ui.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel(
    private val repository: PagingRepository,
    private val pdfRepository: PdfViewRepository
) : BaseViewModel() {


    fun getData(fileName: String, width: Int): Flow<PagingData<Bitmap>> {
        return repository.createDataFlow(fileName, width, PagingConfig(pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_PAGE_SIZE))
            .cachedIn(viewModelScope)
    }

    fun downloadPdfFile(fileUri: String) {
        mStateLiveData.postValue(PdfReaderViewState.Loading(true))
        runAsync {
            val msg = pdfRepository.downloadPdf(fileUri)
            mStateLiveData.postValue(PdfReaderViewState.ReadingFinished(msg))
        }
        Log.d("Tag", "download Pdf file")
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.NewInstanceFactory() {

        @Inject
        lateinit var repository: PagingRepositoryImpl

        @Inject
        lateinit var pdfRepository: PdfViewRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            DI.app.provideComponent().inject(this)
            return MainViewModel(
                repository,
                pdfRepository
            ) as T
        }

    }

}