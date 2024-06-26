package com.example.instock.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.instock.database.Product
import com.example.instock.databinding.ItemNoteBinding
import com.example.instock.helper.ProductDiffCallback
import com.example.instock.ui.insert.ProductAddUpdateActivity

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val listProducts = ArrayList<Product>()
    fun setListProducts(listProducts: List<Product>) {
        val diffCallback = ProductDiffCallback(this.listProducts, listProducts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listProducts.clear()
        this.listProducts.addAll(listProducts)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProducts[position])
    }
    override fun getItemCount(): Int {
        return listProducts.size
    }
    inner class ProductViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                tvItemTitle.text = product.productName
                tvItemDate.text = product.date
                tvItemDescription.text = product.storeName
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, ProductAddUpdateActivity::class.java)
                    intent.putExtra(ProductAddUpdateActivity.EXTRA_NOTE, product)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}