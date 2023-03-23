package com.app.byasa.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.byasa.data.models.ProductColor

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val brand: String,
    val category: String?,
    val description: String,
    val image_link: String,
    val name: String,
    val price: String,
    val product_api_url: String,
    val product_colors: List<ProductColor>?,
    val product_type: String?,
    val rating: Double?,
    val updated_at: String,
    val website_link: String
)
