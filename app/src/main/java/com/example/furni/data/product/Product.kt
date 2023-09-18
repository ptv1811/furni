package com.example.furni.data.product

import com.example.furni.data.base.NetworkObject

data class Product constructor(
    var name: String? = null,
    var description: String? = null,
    var price: String? = null,
    var quantity: String? = null,
    var image: String? = null,
    var sfa: String? = null,
    var sfb: String? = null,
    override val isLoading: Boolean = false,
    override val error: String = ""
) : NetworkObject()