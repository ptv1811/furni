package com.example.furni.data.base

/**
 * @author ltvan@fossil.com
 * on 2023-09-11
 *
 * <p>
 * </p>
 */
open class NetworkObject constructor(
    open val isLoading: Boolean = false,
    open val error: String = ""
)