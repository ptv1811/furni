package com.example.furni.data.store

import com.example.furni.data.base.NetworkObject

data class Store constructor(
    var address: String? = null,
    var phone: String? = null,
    var longitude: String? = null,
    var latitude: String? = null
) : NetworkObject()