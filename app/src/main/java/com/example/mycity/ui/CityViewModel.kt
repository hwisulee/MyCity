package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.local.LocalDataProvider
import com.example.mycity.model.StoreInfo
import com.example.mycity.model.StoreboxType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CityViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState

    fun updateCurrentCategory(selectedCategory: Int) {
        _uiState.update {
            it.copy(
                currentCategory = selectedCategory - 1,
                currentStore = when (selectedCategory) {
                    1 -> LocalDataProvider.getCafeData()
                    2 -> LocalDataProvider.getRestaurantData()
                    3 -> LocalDataProvider.getMallData()
                    else -> LocalDataProvider.getCafeData()
                }
            )
        }
    }

    fun updateCurrentIndex(selectedStoresIndex: Int) {
        _uiState.update {
            it.copy(
                currentIndex = selectedStoresIndex - 1
            )
        }
    }

    fun updateCurrentType(storeBoxType: StoreboxType) {
        _uiState.update {
            it.copy(
                currentType = storeBoxType
            )
        }
    }

    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                currentIndex = 0
            )
        }
    }
}