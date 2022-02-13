package com.caffeine.ecommercefirebase.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.ItemLayoutCateogryBinding
import com.squareup.picasso.Picasso

class CategoryAdapter(private var list: ArrayList<String>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutCateogryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding : ItemLayoutCateogryBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.categoryName
    }
}