package com.example.furni.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.furni.R
import com.example.furni.databinding.ActivityHomeScreenBinding
import com.example.furni.ui.login.LoginActivity
import com.example.furni.viewmodel.AuthViewModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenActivity :
    BindingActivity<ActivityHomeScreenBinding>(R.layout.activity_home_screen),
    OnNavigationItemSelectedListener {

    private val authViewModel: AuthViewModel by viewModels()
    private val navController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            setSupportActionBar(appBar.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setupWithNavController(navController)
//            navView.setNavigationItemSelectedListener(this@HomeScreenActivity)

            NavigationUI.setupActionBarWithNavController(
                this@HomeScreenActivity,
                navController,
                drawerLayout
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main_app, menu)
//        return true
//    }

    override fun onBackPressed() {
        binding {
            drawerLayout.let { drawerLayout ->
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sign_out -> {
                signOut()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOut() {
        authViewModel.logout()
        LoginActivity.startActivity(this@HomeScreenActivity)
        finish()
    }

    companion object {
        fun startActivity(context: Context) {
            Intent(context, HomeScreenActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }
}