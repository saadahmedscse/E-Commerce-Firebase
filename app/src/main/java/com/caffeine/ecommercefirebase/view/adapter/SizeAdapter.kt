package com.caffeine.ecommercefirebase.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.ItemLayoutSizesBinding
import com.caffeine.ecommercefirebase.services.model.Sizes
import com.caffeine.ecommercefirebase.util.OnSizeClick
import com.caffeine.ecommercefirebase.view.fragments.DetailsFragment

class SizeAdapter(val list: ArrayList<Sizes>, val context : Context, var onSizeClick: OnSizeClick) : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var count = 0
    private var detailsFragment: DetailsFragment = DetailsFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutSizesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.root.text = list[position].size

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
            onSizeClick.onSizeClick(holder.root.text.toString())
            count++
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding : ItemLayoutSizesBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
    }
}