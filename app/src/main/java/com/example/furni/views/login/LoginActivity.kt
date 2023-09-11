package com.example.furni.views.login

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.furni.R
import com.example.furni.databinding.ActivityLoginBinding
import com.example.furni.viewmodel.AuthViewModel
import com.example.furni.views.home.HomeScreenActivity
import com.example.furni.views.signup.SignUpActivity
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login),
    OnClickListener {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            loginButton.setOnClickListener(this@LoginActivity)
            signupAct.setOnClickListener(this@LoginActivity)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.user.collect {
                    if (it.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    if (it.error.isNotBlank()) {
                        binding.progressBar.visibility = View.GONE
                        val toast = Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_SHORT)
                        toast.show()
                    }

                    it.user?.let {
                        finish()
                        HomeScreenActivity.startActivity(this@LoginActivity)
                    }
                }
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

            authViewModel.login(email, password)
        }
    }

    private fun signUp() {
        SignUpActivity.startActivity(this@LoginActivity)
    }
}