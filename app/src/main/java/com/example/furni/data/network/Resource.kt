package com.example.furni.data.network

/**
 * Provides a wrapper around API response
 *
 * @param T Expected Response class
 */
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val message: String?,
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}