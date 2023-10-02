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
        pos.takeIf { it != NO_POSITION }?.let {
            ProductDetailActivity.startActivity(binding.root, it + 1)
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