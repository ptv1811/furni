package com.example.furni.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.furni.R
import com.example.furni.data.product.Product
import com.example.furni.databinding.CustomLayoutRecyclerBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ShopItemAdapter(options: FirebaseRecyclerOptions<Product>) :
    FirebaseRecyclerAdapter<Product, ShopItemAdapter.RecyclerViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CustomLayoutRecyclerBinding>(
            inflater, R.layout.custom_layout_recycler, parent, false
        )

        return RecyclerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
                    ?: return@setOnClickListener
                // TODO start ProductDetailActivity
            }
        }
    }

    override fun onBindViewHolder(p0: RecyclerViewHolder, p1: Int, p2: Product) {

    }

    class RecyclerViewHolder(val binding: CustomLayoutRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}