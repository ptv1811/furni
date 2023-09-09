package com.example.furni.repository.login

import com.example.furni.data.LoginResponse

interface LoginRepository {
    suspend fun login(userName: String, password: String): LoginResponse
}