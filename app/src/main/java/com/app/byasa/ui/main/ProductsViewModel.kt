package com.app.byasa.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.byasa.data.local.ProductDao
import com.app.byasa.data.models.ProductItem
import com.app.byasa.data.repository.ProductsRepository
import com.app.byasa.data.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val dao: ProductDao
) : ViewModel() {

    private val _getProducts = MutableStateFlow<Resource<List<ProductItem>>>(Resource.Loading())
    val getProducts = _getProducts.asStateFlow()

    init {
        viewModelScope.launch {
            getAllProducts()
        }
    }

    private suspend fun getAllProducts() {
        repository.getAllProducts().collect {
            _getProducts.emit(it)
        }
    }

    fun getProductById(id: Int): ProductItem {
        return dao.getProduct(id)
    }

}