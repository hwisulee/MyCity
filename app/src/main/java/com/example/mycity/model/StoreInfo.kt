package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class StoreInfo (
    val id: Int,
    val categoryId: Int,
    @StringRes val name: Int,
    @StringRes val address: Int,
    @StringRes val contact: Int,
    @DrawableRes val image: Int
)