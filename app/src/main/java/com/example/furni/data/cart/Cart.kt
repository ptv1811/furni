package com.example.furni.data.cart

import com.example.furni.data.base.NetworkObject

data class Cart(
    var orderID: String = "",
    var productAmount: String = "",
    var productId: String = "",
    var productImage: String = "",
    var productName: String = "",
    var productPrice: String = "",
    override val isLoading: Boolean = false,
    override val error: String = "",
) : NetworkObject()