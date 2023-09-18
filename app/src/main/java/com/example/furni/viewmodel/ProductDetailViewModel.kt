package com.example.furni.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.furni.data.network.Resource
import com.example.furni.data.product.Product
import com.example.furni.repository.product.ProductDetailRepository
import com.skydoves.bindables.BindingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author ltvan@fossil.com
 * on 2023-09-18
 *
 * <p>
 * </p>
 */

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailRepository: ProductDetailRepository
) : BindingViewModel() {

    private val _productDetail = MutableStateFlow(Product())
    val productDetail: StateFlow<Product> = _productDetail

    fun getProductDetail(productUID: String) = viewModelScope.launch {
        productDetailRepository.getProductDetail(productUID).onEach {
            when (it) {
                is Resource.Loading -> {
                    _productDetail.value = Product(isLoading = true)
                }

                is Resource.Failure -> {
                    _productDetail.value = Product(error = it.message ?: "Unknown Error")
                }

                is Resource.Success -> {
                    _productDetail.value = it.value
                }
            }
        }
    }
}