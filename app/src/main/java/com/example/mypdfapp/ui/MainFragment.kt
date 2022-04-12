package com.example.mypdfapp.ui

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mypdfapp.databinding.FragmentMainBinding
import com.example.mypdfapp.extensions.obtainViewModel
import com.example.mypdfapp.ui.adapter.PdfPagingAdapter
import com.example.mypdfapp.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()

        private const val AGREEMENT = "https://kotlinlang.org/docs/kotlin-reference.pdf"

        const val DOCUMENT = "/kotlin-reference.pdf"
    }

    private var bindingNullable: FragmentMainBinding? = null

    private val binding get() = bindingNullable!!

    lateinit var mainViewModel: MainViewModel

    private val pagingAdapter by lazy {
        PdfPagingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingNullable = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = obtainViewModel(MainViewModel.Factory())
        binding.recyclerPdf.adapter = pagingAdapter


        binding.showButton.setOnClickListener {
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath  + DOCUMENT
            Log.d("Tag", path)
            lifecycleScope.launch {
                mainViewModel.getData(path, binding.imagePdf.width).distinctUntilChanged().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }

        binding.loadButton.setOnClickListener {
            mainViewModel.downloadPdfFile(AGREEMENT)
        }
    }
}