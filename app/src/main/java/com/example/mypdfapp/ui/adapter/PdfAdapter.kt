package com.example.mypdfapp.ui.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypdfapp.R
import com.example.mypdfapp.databinding.ItemPdfPageBinding

class PdfAdapter(var items: List<Bitmap> = ArrayList()
) : RecyclerView.Adapter<PdfAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val rvBinding = ItemPdfPageBinding.bind(itemView)

        fun bind(bitmap: Bitmap) = rvBinding.imagePdf.setImageBitmap(bitmap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pdf_page, parent, false))

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun fillList(items: List<Bitmap>) {
        this.items += items
        notifyDataSetChanged()
    }

    fun clear() {
        this.items = emptyList()
    }
}