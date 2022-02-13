package com.caffeine.ecommercefirebase.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.ItemLayoutColorsBinding
import com.caffeine.ecommercefirebase.databinding.ItemLayoutImagesBinding
import com.caffeine.ecommercefirebase.databinding.ItemLayoutSizesBinding
import com.caffeine.ecommercefirebase.services.model.Colors
import com.caffeine.ecommercefirebase.services.model.Images
import com.caffeine.ecommercefirebase.services.model.Sizes
import com.caffeine.ecommercefirebase.util.OnColorClick
import com.caffeine.ecommercefirebase.view.fragments.DetailsFragment
import com.squareup.picasso.Picasso

class ColorAdapter(val list: ArrayList<Colors>, val context : Context, val onColorClick: OnColorClick) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var count = 0
    private var detailsFragment: DetailsFragment = DetailsFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutColorsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.root.text = list[position].color

        if (selectedPosition == position){
            holder.root.setBackgroundResource(R.drawable.bg_green_5)
            holder.root.setTextColor(context.resources.getColor(R.color.background))
        }
        else{
            holder.root.setBackgroundResource(R.drawable.stroke_light_grey_5)
            holder.root.setTextColor(context.resources.getColor(R.color.colorLightGrey))
        }

        if (position == 0 && count == 0){
            holder.root.setBackgroundResource(R.drawable.bg_green_5)
            holder.root.setTextColor(context.resources.getColor(R.color.background))
        }

        holder.root.setOnClickListener{
            onColorClick.onColorClick(holder.root.text.toString())
            count++
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding : ItemLayoutColorsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
    }
}