package com.app.byasa.data.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "products")
@Parcelize
data class ProductItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val api_featured_image: String,
    val brand: String,
    val category: String?,
    val created_at: String,
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
) : Parcelable