package com.example.furni.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.furni.data.network.Resource
import com.example.furni.data.store.Store
import com.example.furni.repository.home.HomeRepository
import com.skydoves.bindables.BindingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author ltvan@fossil.com
 * on 2023-09-11
 *
 * <p>
 * </p>
 */

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BindingViewModel() {

    private val _store = MutableStateFlow(Store())
    val store: StateFlow<Store> = _store

    fun getStoreInformation() = viewModelScope.launch {
        homeRepository.fetchInformationByClass("Shop", Store::class.java).onEach {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Failure -> {
                    _store.value = Store(error = it.message ?: "Unknown Error")
                }

                is Resource.Success -> {
                    _store.value = it.value
                }
            }
        }
    }
}