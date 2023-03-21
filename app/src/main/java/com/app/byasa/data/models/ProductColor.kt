package com.app.byasa.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductColor(
    val colour_name: String,
    val hex_value: String
) : Parcelable