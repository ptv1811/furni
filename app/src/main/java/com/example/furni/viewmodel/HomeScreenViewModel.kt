package com.example.furni.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.furni.data.store.Store
import com.example.furni.repository.home.HomeRepository
import com.skydoves.bindables.BindingViewModel
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
class HomeScreenViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BindingViewModel() {

    private val _store = MutableStateFlow(Store())
    val store: StateFlow<Store> = _store

    fun getStoreInformation() = viewModelScope.launch {
        homeRepository.fetchInformationByClass("Shop", Store::class.java).onEach {
            when (it) {
                
            }
        }
    }
}