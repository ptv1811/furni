package com.example.furni.views.login

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import com.example.furni.R
import com.example.furni.databinding.ActivityLoginBinding
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login), OnClickListener {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            loginButton.setOnClickListener(this@LoginActivity)
            signupAct.setOnClickListener(this@LoginActivity)
        }

        lifecycle.coroutineScope.launchWhenCreated {
            loginViewModel.user.collect {
                // TODO
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_button -> {
                login()
            }
            R.id.signup_act -> {
                signUp()
            }
        }
    }

    private fun login() {
        binding {
            val email = username.text.toString()
            val password = password.text.toString()

            loginViewModel.login(email, password)
        }
    }

    private fun signUp() {

    }
}