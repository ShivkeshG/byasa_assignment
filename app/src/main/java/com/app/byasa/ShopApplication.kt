package com.app.byasa

import android.app.Application
import com.app.byasa.data.localDataSource.ProductDatabase
import com.app.byasa.data.repository.ProductsRepository
import com.app.byasa.data.retrofit.RetrofitInstance
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopApplication: Application() {

    lateinit var productsRepository: ProductsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val api = RetrofitInstance.api
        val database = ProductDatabase.getDatabase(applicationContext).productDao()
        productsRepository = ProductsRepository(database, api)
    }
}