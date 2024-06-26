package com.example.instock.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.instock.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Product::class], version = 3)
abstract class ProductRoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ProductRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductRoomDatabase::class.java,
                    "product_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                populateDatabase(database.productDao(), context)
            }
        }

        private fun populateDatabase(productDao: ProductDao, context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                // Add initial data here
                val productRepository = ProductRepository(context.applicationContext as Application)
                val products = listOf(
                    Product(
                        productName = "Sample Product",
                        storeName = "Shopee",
                        stock = 100,
                        sales = 0,
                        date = "2023-01-01"
                    ),
                    Product(
                        productName = "Sample Product",
                        storeName = "Tokopedia",
                        stock = 100,
                        sales = 0,
                        date = "2023-01-01"
                    ),
                    Product(
                        productName = "Sample Product",
                        storeName = "Zalora",
                        stock = 100,
                        sales = 0,
                        date = "2023-01-01"
                    )
                )
                products.forEach { productRepository.insert(it) }
            }
        }
    }


}
