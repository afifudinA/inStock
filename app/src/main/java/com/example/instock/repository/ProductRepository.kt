package com.example.instock.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.instock.database.Product
import com.example.instock.database.ProductDao
import com.example.instock.database.ProductRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProductRepository(application: Application) {
    private val mNotesDao: ProductDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = ProductRoomDatabase.getDatabase(application)
        mNotesDao = db.productDao()
    }
    fun getAllProduct(): LiveData<List<Product>> = mNotesDao.getAllProducts()
    fun insert(note: Product) {
        executorService.execute { mNotesDao.insert(note) }
    }
    fun delete(note: Product) {
        executorService.execute { mNotesDao.delete(note) }
    }
    fun update(note: Product) {
        executorService.execute { mNotesDao.update(note) }
    }
}