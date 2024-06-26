package com.example.instock.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Entity
@kotlinx.parcelize.Parcelize
data class Product(
    @ColumnInfo(name = "product_name")
    var productName: String,
    @PrimaryKey
    @ColumnInfo(name = "store_name")
    var storeName: String,
    @ColumnInfo(name = "stock")
    var stock: Int? = 0,
    @ColumnInfo(name = "sales")
    var sales: Int? = 0,
    @ColumnInfo(name = "date")
    var date: String? = null
):Parcelable