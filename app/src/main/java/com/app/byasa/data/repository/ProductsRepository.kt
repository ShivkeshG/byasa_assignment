package com.app.byasa.data.repository

import com.app.byasa.data.local.ProductDatabase
import com.app.byasa.data.models.ProductItem
import com.app.byasa.data.retrofit.ProductsApi
import com.app.byasa.data.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val api: ProductsApi,
    db: ProductDatabase
) {
    private val dao = db.productDao()

    suspend fun getAllProducts(): Flow<Resource<List<ProductItem>>> {
        return flow {
            emit(Resource.Loading())
            val localProducts = dao.getAllProducts()
            emit(Resource.Success(localProducts))

            val isDbEmpty = localProducts.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty
            if (shouldJustLoadFromCache) {
                return@flow
            }

            try {
                val response = api.getAllProducts()
                response.body()?.let { dao.insertProduct(it) }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }


}