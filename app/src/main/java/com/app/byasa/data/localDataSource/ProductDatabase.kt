package com.app.byasa.data.localDataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.byasa.data.models.ProductItem
import com.app.byasa.data.utils.Converters

@Database(entities = [ProductItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): ProductDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "products"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return INSTANCE!!
        }
    }
}