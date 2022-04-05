package com.example.mypdfapp.viewmodel

import com.example.mypdfapp.model.viewstate.PdfReaderViewState

class MainViewModel : BaseViewModel() {

    fun getPdfPage() {
        mStateLiveData.postValue(PdfReaderViewState.Loading(true))
        runAsync {
            //TODO что-то там загружается
        }
    }

}