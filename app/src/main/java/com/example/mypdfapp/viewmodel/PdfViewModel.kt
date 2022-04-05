package com.example.mypdfapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypdfapp.di.DI
import com.example.mypdfapp.model.viewstate.PdfReaderViewState
import com.example.mypdfapp.repository.PdfViewRepository
import javax.inject.Inject

class PdfViewModel(private val repository: PdfViewRepository) : BaseViewModel() {

    fun renderPage(filePath: String, pageNumber: Int, width: Int) {
        mStateLiveData.postValue(PdfReaderViewState.Loading(true))
        runAsync {
            //val bitmap = repository.renderSinglePage(filePath, pageNumber, width)
            //mStateLiveData.postValue(PdfReaderViewState.RenderingSuccess(bitmap))
        }
    }

    fun renderPages(filePath: String, width: Int) {
        mStateLiveData.postValue(PdfReaderViewState.Loading(true))
        runAsync {
            val bitmap = repository.renderAllPages(filePath, 0, 16, width)
            mStateLiveData.postValue(PdfReaderViewState.RenderingSuccess(bitmap))
        }
    }

    fun downloadPdfFile(fileUri: String) {
        mStateLiveData.postValue(PdfReaderViewState.Loading(true))
        runAsync {
            val msg = repository.downloadPdf(fileUri)
            mStateLiveData.postValue(PdfReaderViewState.ReadingFinished(msg))
        }
        Log.d("Tag", "download Pdf file")
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.NewInstanceFactory() {

        @Inject
        lateinit var repository: PdfViewRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            DI.app.provideComponent().inject(this)
            return PdfViewModel(
                repository
            ) as T
        }

    }
}