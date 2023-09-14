package com.example.furni.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furni.R
import com.example.furni.databinding.FragmentShopAllBinding
import com.example.furni.viewmodel.HomeScreenViewModel
import com.skydoves.bindables.BindingFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author ltvan@fossil.com
 * on 2023-09-13
 *
 * <p>
 * </p>
 */
class ShopFragment : BindingFragment<FragmentShopAllBinding>(R.layout.fragment_shop_all) {

    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()
    private lateinit var adapter: ShopItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShopItemAdapter()
        binding {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeScreenViewModel.productList.collect {
                    // TODO: handle loading and error
                    adapter.setProductList(it.productList)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadProductList()
    }

    private fun loadProductList() {
        homeScreenViewModel.getProductList()
    }
}