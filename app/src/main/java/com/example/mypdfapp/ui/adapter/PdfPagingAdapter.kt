package com.example.mypdfapp.ui.adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mypdfapp.R
import com.example.mypdfapp.databinding.ItemPdfPageBinding

class PdfPagingAdapter : PagingDataAdapter<Bitmap, PdfPagingAdapter.PdfPagingViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: PdfPagingViewHolder, position: Int) {
        Log.d("Tag", "onBindViewHolder $position")
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfPagingViewHolder {
        return PdfPagingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pdf_page,
            parent,
            false))
    }

    inner class PdfPagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPdfPageBinding.bind(itemView)

        fun bind(data: Bitmap?) {
            with(binding) {
                imagePdf.setImageBitmap(data)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Bitmap>() {
            override fun areItemsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean =
                oldItem.sameAs(newItem)
        }
    }
}