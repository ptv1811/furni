package com.example.furni.ui.fragments

import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.furni.R
import com.example.furni.data.product.Product
import com.example.furni.databinding.CustomLayoutRecyclerBinding
import com.example.furni.ui.product.ProductDetailActivity
import com.squareup.picasso.Picasso

class ShopItemAdapter : BaseRecyclerViewAdapter<Product, CustomLayoutRecyclerBinding>(
    R.layout.custom_layout_recycler,
    clickListener = { pos, binding ->
        val position = pos.takeIf { it != NO_POSITION }
        if (position != null) {
            ProductDetailActivity.startActivity(binding.root, position)
        }
    },

    onBindViewHolderCallback = { product, binding ->
        binding.apply {
            Picasso.get().load(product.image).into(productImage)
            productName.text = product.name
            productPrice.text = product.price
        }
    }
)