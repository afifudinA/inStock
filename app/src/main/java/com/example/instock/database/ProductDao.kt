package com.example.instock.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product)
    @Update
    fun update(product: Product)
    @Delete
    fun delete(product: Product)
    @Query("SELECT * from product ORDER BY product_name ASC")
    fun getAllProducts(): LiveData<List<Product>>
}