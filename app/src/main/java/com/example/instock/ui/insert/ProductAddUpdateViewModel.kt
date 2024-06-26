package com.example.instock.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.instock.database.Product
import com.example.instock.repository.ProductRepository


class ProductAddUpdateViewModel(application: Application) : ViewModel() {
    private val mProductRepository: ProductRepository = ProductRepository(application)
    fun insert(product: Product) {
        mProductRepository.insert(product)
    }
    fun update(product: Product) {
        mProductRepository.update(product)
    }
    fun delete(product: Product) {
        mProductRepository.delete(product)
    }
}