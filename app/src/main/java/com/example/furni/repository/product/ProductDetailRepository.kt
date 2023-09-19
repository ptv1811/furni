package com.example.furni.repository.product

import com.example.furni.data.network.Resource
import com.example.furni.data.order.OrderStatus
import com.example.furni.data.product.Product
import com.example.furni.data.product.Products
import kotlinx.coroutines.flow.Flow

/**
 * @author ltvan@fossil.com
 * on 2023-09-18
 *
 * <p>
 * </p>
 */
interface ProductDetailRepository {

    suspend fun getProductDetail(productUID: String): Flow<Resource<Product>>
    suspend fun addToCart(userID: String, productID: String, product: Product, amount: Int): Flow<Resource<OrderStatus>>
}