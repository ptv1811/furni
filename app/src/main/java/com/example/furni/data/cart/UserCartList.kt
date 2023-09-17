package com.example.furni.data.cart

import com.example.furni.data.base.NetworkObject

data class UserCartList(
    val cartList: List<Cart> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: String = ""
) : NetworkObject()