package com.example.furni.repository.product

import com.example.furni.data.network.Resource
import com.example.furni.data.product.Product
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
}