package com.example.mycity.data.local

import com.example.mycity.R
import com.example.mycity.model.StoreInfo

object LocalDataProvider {
    val defaultCafe = getCafeData()[0]

    fun getCategoryData(): List<StoreInfo> {
        return listOf(
            StoreInfo(
                id = 1,
                categoryId = 1,
                name = R.string.cafe01_name,
                address = R.string.cafe01_contact,
                contact = R.string.cafe01_contact,
                image = R.drawable.cafe
            ),
            StoreInfo(
                id = 2,
                categoryId = 2,
                name = R.string.cafe01_name,
                address = R.string.cafe01_adress,
                contact = R.string.cafe01_contact,
                image = R.drawable.restaurant
            ),
            StoreInfo(
                id = 3,
                categoryId = 3,
                name = R.string.cafe01_name,
                address = R.string.cafe01_adress,
                contact = R.string.cafe01_contact,
                image = R.drawable.mall
            )
        )
    }

    fun getCafeData(): List<StoreInfo> {
        return listOf(
            StoreInfo(
                id = 1,
                categoryId = 1,
                name = R.string.cafe01_name,
                address = R.string.cafe01_adress,
                contact = R.string.cafe01_contact,
                image = R.drawable.cafe
            ),
            StoreInfo(
                id = 2,
                categoryId = 1,
                name = R.string.cafe02_name,
                address = R.string.cafe02_adress,
                contact = R.string.cafe02_contact,
                image = R.drawable.cafe
            ),
            StoreInfo(
                id = 3,
                categoryId = 1,
                name = R.string.cafe03_name,
                address = R.string.cafe03_adress,
                contact = R.string.cafe03_contact,
                image = R.drawable.cafe
            )
        )
    }

    fun getRestaurantData(): List<StoreInfo> {
        return listOf(
            StoreInfo(
                id = 1,
                categoryId = 2,
                name = R.string.restaurant01_name,
                address = R.string.cafe01_adress,
                contact = R.string.cafe01_contact,
                image = R.drawable.restaurant
            ),
            StoreInfo(
                id = 2,
                categoryId = 2,
                name = R.string.restaurant02_name,
                address = R.string.cafe02_adress,
                contact = R.string.cafe02_contact,
                image = R.drawable.restaurant
            ),
            StoreInfo(
                id = 3,
                categoryId = 2,
                name = R.string.restaurant03_name,
                address = R.string.cafe03_adress,
                contact = R.string.cafe03_contact,
                image = R.drawable.restaurant
            )
        )
    }

    fun getMallData(): List<StoreInfo> {
        return listOf(
            StoreInfo(
                id = 1,
                categoryId = 3,
                name = R.string.mall01_name,
                address = R.string.cafe01_adress,
                contact = R.string.cafe01_contact,
                image = R.drawable.mall
            ),
            StoreInfo(
                id = 2,
                categoryId = 3,
                name = R.string.mall02_name,
                address = R.string.cafe02_adress,
                contact = R.string.cafe02_contact,
                image = R.drawable.mall
            ),
            StoreInfo(
                id = 3,
                categoryId = 3,
                name = R.string.mall03_name,
                address = R.string.cafe03_adress,
                contact = R.string.cafe03_contact,
                image = R.drawable.mall
            )
        )
    }
}