package com.app.byasa.data.repository

import com.app.byasa.data.localDataSource.ProductDao
import com.app.byasa.data.models.ProductItem
import com.app.byasa.data.retrofit.ProductsApi
import com.app.byasa.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productApi: ProductsApi,
) {

    private val _productsFlow = MutableStateFlow<Resource<List<ProductItem>>>(Resource.Loading())

    val products = _productsFlow.asStateFlow()


    suspend fun getProducts() {
        val result = productApi.getAllProducts()
        try {
            if (result.isSuccessful && result.body() != null) {
                // Add all api products to local products
                productDao.insertProduct(result.body()!!)
                // Get all local products
                val localProducts = productDao.getAllProducts()
                _productsFlow.emit(Resource.Success(localProducts))
            }
        } catch (e: HttpException) {
            val localProducts = productDao.getAllProducts()
            _productsFlow.emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = localProducts
                )
            )
        } catch (e: IOException) {
            val localProducts = productDao.getAllProducts()
            _productsFlow.emit(
                Resource.Success(
                    data = localProducts
                )
            )
        }
        val data = products.value
    }


}