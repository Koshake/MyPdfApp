package com.example.mypdfapp.ui.base

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mypdfapp.model.viewstate.PdfReaderViewState
import com.example.mypdfapp.viewmodel.BaseViewModel
import java.io.File

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM

    protected open fun renderData(state: PdfReaderViewState) {
        when (state) {
            is PdfReaderViewState.ReadingFinished -> downloadFinished(state.msg)
            is PdfReaderViewState.RenderingSuccess -> renderSuccess(state.data)
            is PdfReaderViewState.Error -> renderError(state.error)
            is PdfReaderViewState.Loading -> setLoading(true)
        }
    }

    protected open fun downloadFinished(message: String) {
        setLoading(false)
        showMessage(message)
    }

    protected open fun renderSuccess(bitmap: List<Bitmap>) {
        setLoading(false)
        Log.d("Tag", "render success!!!")
    }

    protected open fun renderError(error: Throwable) {
        setLoading(false)
        Log.d("Tag", "render error!!!")
        error.message?.let { showMessage(it) }
    }

    protected open fun renderMessage(message: String) {
        setLoading(false)
        showMessage(message)
    }

    protected open fun setLoading(isLoading: Boolean) {
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}