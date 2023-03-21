package com.app.byasa.data.localDataSource

import androidx.room.*
import com.app.byasa.data.models.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<ProductItem>)

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProduct(productId: Int): ProductItem
}