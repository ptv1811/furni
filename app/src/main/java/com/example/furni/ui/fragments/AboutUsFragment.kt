package com.example.furni.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.furni.R
import com.example.furni.data.aboutus.AboutUs
import com.example.furni.databinding.FragmentAboutUsBinding
import com.example.furni.viewmodel.HomeScreenViewModel
import com.skydoves.bindables.BindingFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author ltvan@fossil.com
 * on 2023-09-13
 *
 * <p>
 * </p>
 */
class AboutUsFragment : BindingFragment<FragmentAboutUsBinding>(R.layout.fragment_about_us) {
    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.let {
            it.lifecycleScope.launch {
                it.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeScreenViewModel.aboutUs.collectLatest { aboutUs ->
                        if (aboutUs.isLoading) {
                            binding.pBar.visibility = View.VISIBLE
                        } else if (aboutUs.error.isNotBlank()) {
                            binding.pBar.visibility = View.GONE
                            val toast =
                                Toast.makeText(activity, aboutUs.error, Toast.LENGTH_SHORT)
                            toast.show()
                        } else {
                            binding.pBar.visibility = View.GONE
                            updateAboutUsInformation(aboutUs)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeScreenViewModel.getAboutUsInformation()
    }

    private fun updateAboutUsInformation(aboutUs: AboutUs) {
        binding {
            title.text = aboutUs.title
            content.text = aboutUs.content
        }
    }
}