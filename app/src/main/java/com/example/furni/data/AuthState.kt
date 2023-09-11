package com.example.furni.data

import com.example.furni.data.base.NetworkObject
import com.google.firebase.auth.FirebaseUser

data class AuthState constructor(
    val user: FirebaseUser? = null
) : NetworkObject() {
    constructor(isLoading: Boolean) : this() {
        super(isLoading)
    }
}