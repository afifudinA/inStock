package com.example.instock.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.instock.database.Product

class ProductDiffCallback(private val oldNoteList: List<Product>, private val newNoteList: List<Product>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].productName == newNoteList[newItemPosition].productName
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.productName == newNote.productName && oldNote.storeName == newNote.storeName
    }
}