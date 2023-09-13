package com.example.furni.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.furni.R
import com.example.furni.databinding.FragmentShopAllBinding
import com.example.furni.viewmodel.HomeScreenViewModel
import com.skydoves.bindables.BindingFragment

/**
 * @author ltvan@fossil.com
 * on 2023-09-13
 *
 * <p>
 * </p>
 */
class ShopFragment : BindingFragment<FragmentShopAllBinding>(R.layout.fragment_shop_all) {

    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}