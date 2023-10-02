package com.example.furni.repository.login

import com.example.furni.data.AuthState
import com.example.furni.data.network.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(userName: String, password: String): Flow<Resource<AuthState>>

    suspend fun register(userName: String, password: String): Flow<Resource<AuthState>>

    suspend fun logout()

    suspend fun  isUserLoggedIn(): Flow<Resource<AuthState>>
}