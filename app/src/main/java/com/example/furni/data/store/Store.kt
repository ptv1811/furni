package com.example.furni.data.store

import com.example.furni.data.base.NetworkObject

data class Store constructor(
    var address: String = "",
    var phone: String = "",
    var longitude: String = "",
    var latitude: String = "",
    override val isLoading: Boolean = false,
    override val error: String = "",
) : NetworkObject()