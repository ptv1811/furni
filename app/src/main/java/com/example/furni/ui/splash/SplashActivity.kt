package com.example.furni.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.furni.R
import com.example.furni.databinding.ActivitySplashBinding
import com.example.furni.ui.home.HomeScreenActivity
import com.example.furni.ui.login.LoginActivity
import com.example.furni.viewmodel.SplashViewModel
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                splashViewModel.user.collectLatest {
                    it?.let { authState ->
                        if (authState.error.isNotBlank()) {
                            val toast =
                                Toast.makeText(this@SplashActivity, it.error, Toast.LENGTH_SHORT)
                            toast.show()
                        } else {
                            if (authState.user == null) {
                                LoginActivity.startActivity(this@SplashActivity)
                            } else {
                                HomeScreenActivity.startActivity(this@SplashActivity)
                            }
                            finish()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        splashViewModel.checkLoggedUser()
    }
}