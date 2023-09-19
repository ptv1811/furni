package com.example.furni.data.order

import com.example.furni.data.base.NetworkObject

class Order constructor(
    val productId: String = "",
    val productName: String = "",
    val productAmount: String = "",
)

data class OrderStatus constructor(
    val status: String = "",
    override val isLoading: Boolean = false,
    override val error: String = ""
) : NetworkObject()