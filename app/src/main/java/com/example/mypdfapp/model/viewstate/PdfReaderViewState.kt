package com.example.mypdfapp.model.viewstate

import android.graphics.Bitmap

sealed class PdfReaderViewState {
    data class ReadingFinished(val msg : String) : PdfReaderViewState()
    data class RenderingSuccess(val data: List<Bitmap>) : PdfReaderViewState()
    data class Error(val error: Throwable) : PdfReaderViewState()
    data class Loading(val isLoading: Boolean) : PdfReaderViewState()
}