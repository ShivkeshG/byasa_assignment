package com.app.byasa.data.retrofit

import com.app.byasa.data.models.Product
import com.app.byasa.data.models.ProductItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {

    @GET("api/v1/products.json?brand=maybelline")
    suspend fun getAllProducts(): Response<List<ProductItem>>
}