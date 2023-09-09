package com.example.furni.repository.login

import com.example.furni.data.LoginResponse
import com.example.furni.data.User

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(userName: String, password: String): LoginResponse {
        // TODO
        return LoginResponse(User())
    }
}