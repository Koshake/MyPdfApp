package com.example.mypdfapp.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory? = null
): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}