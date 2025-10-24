package com.example.loginapp

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CarruselAdapter(private val imagenes: List<Int>) :
    RecyclerView.Adapter<CarruselAdapter.ImageViewHolder>() {

    class ImageViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageView = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ImageViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imagenes[position])
    }

    override fun getItemCount() = imagenes.size
}