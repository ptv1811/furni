package com.example.furni.views.login

import androidx.lifecycle.viewModelScope
import com.example.furni.data.AuthState
import com.example.furni.data.network.Resource
import com.example.furni.repository.login.AuthRepository
import com.skydoves.bindables.BindingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val authRepository: AuthRepository)
    : BindingViewModel() {

    private val _user = MutableStateFlow(AuthState())
    val user: StateFlow<AuthState> = _user

    fun login(email: String, password: String) = viewModelScope.launch {
        authRepository.login(email, password).onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = AuthState(isLoading =  true)
                }
                is Resource.Failure -> {
                    _user.value = AuthState(error = it.message ?: "Unknown Error")
                }
                is Resource.Success -> {
                    _user.value = AuthState(user = it.value.user)
                }
            }
        }
    }
}