package com.example.furni.repository.home

import com.example.furni.data.network.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author ltvan@fossil.com
 * on 2023-09-11
 *
 * <p>
 * </p>
 */
interface HomeRepository {
    suspend fun <T : Any> fetchInformationByClass(
        path: String,
        typeClass: Class<T>
    ): Flow<Resource<T>>

    suspend fun <T : Any> fetchListInformationByClass(
        path: String,
        typeClass: Class<T>
    ): Flow<Resource<List<T>>>
}