package com.app.byasa.di

import android.content.Context
import com.app.byasa.data.local.ProductDao
import com.app.byasa.data.local.ProductDatabase
import com.app.byasa.data.retrofit.ProductsApi
import com.app.byasa.data.retrofit.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ProductDatabase {
        return ProductDatabase.getDatabase(context)
    }

    @Provides
    fun provideProductDao(appDatabase: ProductDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    fun provideProductApi(): ProductsApi {
        return RetrofitInstance.api
    }
}