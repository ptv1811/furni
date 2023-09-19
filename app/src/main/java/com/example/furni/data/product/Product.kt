package com.example.furni.data.product

import com.example.furni.data.base.NetworkObject

data class Product constructor(
    var name: String = "",
    var description: String = "",
    var price: String = "",
    var quantity: String = "",
    var image: String = "",
    var sfa: String = "",
    var sfb: String = "",
    override val isLoading: Boolean = false,
    override val error: String = ""
) : NetworkObject()