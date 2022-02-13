package com.caffeine.ecommercefirebase.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.ecommercefirebase.databinding.ItemLayoutImagesBinding
import com.caffeine.ecommercefirebase.services.model.Images
import com.squareup.picasso.Picasso

class ImageAdapter(val list: ArrayList<Images>, val image : ImageView) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(list[position].url).into(holder.image)
        holder.root.setOnClickListener{
            Picasso.get().load(list[position].url).into(image)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding : ItemLayoutImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val image = binding.productVariantImage
    }
}