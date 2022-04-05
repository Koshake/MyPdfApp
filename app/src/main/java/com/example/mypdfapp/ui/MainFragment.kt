package com.example.mypdfapp.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mypdfapp.databinding.FragmentMainBinding
import com.example.mypdfapp.extensions.obtainViewModel
import com.example.mypdfapp.ui.adapter.PdfAdapter
import com.example.mypdfapp.ui.base.BaseFragment
import com.example.mypdfapp.viewmodel.PdfViewModel

class MainFragment : BaseFragment<PdfViewModel>() {

    companion object {
        fun newInstance() = MainFragment()

        private const val AGREEMENT = "https://kotlinlang.org/docs/kotlin-reference.pdf"

        private const val DOCUMENT = "/kotlin-reference.pdf"

    }

    private var bindingNullable: FragmentMainBinding? = null

    private val binding get() = bindingNullable!!

    override lateinit var viewModel: PdfViewModel

    private var adapter : PdfAdapter? = null

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

        viewModel = obtainViewModel(PdfViewModel.Factory())

        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }

        binding.loadButton.setOnClickListener {
            viewModel.downloadPdfFile(AGREEMENT)
            Log.d("Tag", "MainFragment clickListener")
        }

        binding.showButton.setOnClickListener {
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath  + DOCUMENT
            Log.d("Tag", path)
            viewModel.renderPages(
                path,
                binding.imagePdf.width)
        }
        
        initAdapter()
    }

    override fun renderSuccess(bitmap: List<Bitmap>) {
        super.renderSuccess(bitmap)
        adapter?.let {
            it.clear()
            it.fillList(bitmap)
        }
    }
    override fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    private fun showLoading() {
        binding.progressBar.show()
        binding.progressBar.show()
    }

    private fun hideLoading() {
        if (binding.progressBar.isShown) {
            binding.progressBar.hide()
        }
        if (binding.progressBar.isShown) {
            binding.progressBar.hide()
        }
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = PdfAdapter()
            binding.recyclerPdf.adapter = adapter
        }
    }
}