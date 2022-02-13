package com.caffeine.ecommercefirebase.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.ecommercefirebase.databinding.ItemLayoutProductBinding
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.squareup.picasso.Picasso

class ProductAdapter(val context: Context, val list: ArrayList<ProductDetails>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutProductBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        Picasso.get().load(product.images?.get(0)?.url).into(holder.image)
        holder.title.text = product.name
        holder.desc.text = product.desc
        holder.price.text = "$" + product.price

        holder.image.setOnClickListener{
            Toast.makeText(context, "id: ${product.id}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding : ItemLayoutProductBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.productImage
        val title = binding.productTitle
        val desc = binding.productDesc
        val price = binding.productPrice
    }
}