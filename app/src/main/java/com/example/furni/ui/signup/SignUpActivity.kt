package com.example.furni.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.furni.R
import com.example.furni.databinding.ActivitySignUpBinding
import com.example.furni.viewmodel.AuthViewModel
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up),
    OnClickListener {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            moveSignin.setOnClickListener(this@SignUpActivity)
            signUp.setOnClickListener(this@SignUpActivity)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.user.collect {
                    if (it.isLoading) {
                        binding.pBar.visibility = VISIBLE
                    }

                    if (it.error.isNotBlank()) {
                        binding.pBar.visibility = GONE
                        val toast =
                            Toast.makeText(this@SignUpActivity, it.error, Toast.LENGTH_SHORT)
                        toast.show()
                    }

                    it.user?.let {
                        finish()
                        // TODO: Start MainActivity
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.move_signin -> {
                finish()
            }

            R.id.button_signup -> {
                register()
            }
        }
    }

    private fun register() {
        binding {
            val email = suNemail.text.toString()
            val password = suNpass.text.toString()
            val confirmPassword = suNcpass.text.toString()

            if (email.isEmpty()) {
                suNemail.error = "Please type in your email"
                suNemail.requestFocus()
                return
            }

            if (password.isEmpty()) {
                suNpass.error = "Please type in password"
                suNpass.requestFocus()
                return
            }

            if (confirmPassword.isEmpty()) {
                suNcpass.error = "Please type in confirm password"
                suNcpass.requestFocus()
                return
            }

            if (password != confirmPassword) {
                suNcpass.error = "Confirm password must match password"
                suNcpass.requestFocus()
                return
            }

            authViewModel.register(email, password)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }
}