package com.caffeine.ecommercefirebase.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.ecommercefirebase.databinding.ItemLayoutCartBinding
import com.caffeine.ecommercefirebase.helper.AlertDialog
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.caffeine.ecommercefirebase.view.fragments.CartFragment
import com.caffeine.ecommercefirebase.viewmodel.CartViewModel
import com.squareup.picasso.Picasso

class CartAdapter(
    val context : Context,
    val list : ArrayList<CartModel>,
    val sub : TextView,
    val total : TextView,
    val owner : LifecycleOwner,
    val cartViewModel : CartViewModel,
    val checkOut : RelativeLayout,
    val btnText : TextView,
    val progressBar : ProgressBar) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    val priceList : ArrayList<Int> = ArrayList()

    class ViewHolder(binding : ItemLayoutCartBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.cartImage
        val name = binding.cartName
        val size = binding.cartSize
        val color = binding.cartColor
        val price = binding.cartPrice
        val quantity = binding.quantity
        val delete = binding.deleteCart
        val plus = binding.plus
        val minus = binding.minus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutCartBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cart = list[position]

        Picasso.get().load(cart.image).into(holder.image)
        holder.name.text = cart.name
        holder.size.text = "Size: ${cart.size}"
        holder.color.text = "Color: ${cart.color}"
        holder.price.text = "৳${cart.price}"
        holder.quantity.text = cart.quantity
        holder.delete.setOnClickListener{
            cartViewModel.removeCart(cart.id!!)
            cartViewModel.removeCart.observe(owner){
                when(it){
                    is DataState.Loading -> {}

                    is DataState.Success -> {
                        Toast.makeText(context, it.data, Toast.LENGTH_SHORT).show()
                    }

                    is DataState.Failed -> {
                        AlertDialog.getInstance(context).showAlertDialog(it.message!!, "Close")
                    }
                }
            }
        }

        var currentPrice = cart.price?.toInt()
        var quantity = holder.quantity.text.toString().toInt()
        val mainPrice = cart.price?.toInt()?.div(quantity)

        holder.plus.setOnClickListener{
            if (quantity < 5){
                quantity++
                currentPrice = mainPrice?.times(quantity)
                priceList[position] += mainPrice!!
                updatePrice()
                holder.price.text = "$$currentPrice"
                holder.quantity.text = quantity.toString()
            }
        }

        holder.minus.setOnClickListener{
            if (quantity != 1){
                quantity--
                currentPrice = currentPrice?.minus(mainPrice!!)
                priceList[position] = currentPrice!!
                updatePrice()
                holder.price.text = "$$currentPrice"
                holder.quantity.text = quantity.toString()
            }
        }

        priceList.add(list[position].price?.toInt()!!)
        var tot = 0
        for (p in priceList){
            tot += p
        }

        sub.text = "$$tot"
        this.total.text = "৳${tot + 100}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    fun updatePrice(){
        var total = 0
        for (p in priceList){
            total += p
        }

        sub.text = "৳$total"
        this.total.text = "৳${total + 100}"
    }
}