package com.example.instock.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instock.database.Product
import com.example.instock.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        repository = ProductRepository(application)
        allProducts = repository.getAllProduct()
    }

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun delete(product: Product) = viewModelScope.launch {
        repository.delete(product)
    }

    fun update(product: Product) = viewModelScope.launch {
        repository.update(product)
    }

}