package com.example.furni.ui.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.furni.R
import com.example.furni.data.product.Product
import com.example.furni.databinding.CustomLayoutRecyclerBinding
import com.example.furni.ui.product.ProductDetailActivity
import com.squareup.picasso.Picasso

class ShopItemAdapter :
    RecyclerView.Adapter<ShopItemAdapter.RecyclerViewHolder>() {

    private val items: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CustomLayoutRecyclerBinding>(
            inflater, R.layout.custom_layout_recycler, parent, false
        )

        return RecyclerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
                    ?: return@setOnClickListener
                ProductDetailActivity.startActivity(binding.root, position)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(productList: List<Product>) {
        items.clear()
        items.addAll(productList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.binding.apply {
            val product = items[position]
            Picasso.get().load(product.image).into(productImage)
            productName.text = product.name
            productPrice.text = product.price
        }
    }

    class RecyclerViewHolder(val binding: CustomLayoutRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}