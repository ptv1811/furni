package com.example.furni.data.product

import com.example.furni.data.base.NetworkObject

data class Products(
    val productList: List<Product> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: String = "",
) : NetworkObject()