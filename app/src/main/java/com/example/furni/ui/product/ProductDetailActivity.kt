package com.example.furni.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.furni.R
import com.example.furni.databinding.ActivityProductDetailBinding
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity :
    BindingActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun startActivity(view: View, productId: Int) {
            Intent(view.context, ProductDetailActivity::class.java).also {
                it.putExtra("ProductId", productId)
                view.context.startActivity(it)
            }
        }
    }
}