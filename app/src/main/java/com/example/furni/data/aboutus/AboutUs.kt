package com.example.furni.data.aboutus

import com.example.furni.data.base.NetworkObject

/**
 * @author ltvan@fossil.com
 * on 2023-09-13
 *
 * <p>
 * </p>
 */
data class AboutUs constructor(
    val title: String = "",
    val content: String = "",
    override val isLoading: Boolean = false,
    override val error: String = "",
) : NetworkObject()