package com.example.furni.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.furni.R
import com.example.furni.databinding.FragmentCartBinding
import com.example.furni.viewmodel.HomeScreenViewModel
import com.skydoves.bindables.BindingFragment

class CartFragment: BindingFragment<FragmentCartBinding>(R.layout.fragment_cart) {

    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}