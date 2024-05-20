package com.example.mycity.ui

import com.example.mycity.data.local.LocalDataProvider
import com.example.mycity.model.StoreInfo
import com.example.mycity.model.StoreboxType

data class CityUiState(
    val currentCategory: Int = 0,
    val currentIndex: Int = 0,
    val currentStore: List<StoreInfo> = LocalDataProvider.getCafeData(),
    val currentType: StoreboxType = StoreboxType.Cafe
)