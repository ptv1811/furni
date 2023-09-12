package com.example.furni.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.furni.R
import com.example.furni.databinding.ActivityHomeScreenBinding
import com.example.furni.viewmodel.HomeScreenViewModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenActivity :
    BindingActivity<ActivityHomeScreenBinding>(R.layout.activity_home_screen),
    OnNavigationItemSelectedListener {

    private val homeViewModel: HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            setSupportActionBar(appBar.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setupWithNavController(navHostFragment.findNavController())
            navView.setNavigationItemSelectedListener(this@HomeScreenActivity)

            NavigationUI.setupActionBarWithNavController(
                this@HomeScreenActivity,
                navHostFragment.findNavController(),
                drawerLayout
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

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
//        when (item.itemId) {
//            R.id.nav_shop -> {
//                // Fragment shop
//            }
//
//            R.id.nav_about_us -> {
//                // Fragment about us
//            }
//
//            R.id.nav_store -> {
//                // Fragment store
//            }
//
//            R.id.nav_cart -> {
//                // Fragment cart
//            }
//
//            R.id.nav_sign_out -> {
//
//            }
//        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
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