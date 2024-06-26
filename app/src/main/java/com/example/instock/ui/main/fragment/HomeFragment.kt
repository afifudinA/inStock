package com.example.instock.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instock.R
import com.example.instock.database.Product
import com.example.instock.databinding.FragmentHomeBinding
import com.example.instock.helper.ViewModelFactory
import com.example.instock.ui.main.MainViewModel

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val productViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel = obtainViewModel(requireActivity())

        // Observe LiveData
        productViewModel.allProducts.observe(viewLifecycleOwner, Observer { products ->
            Log.d("TAG PRODUCT", "onViewCreated: $products")

            // Update UI with the list of products
            products?.forEach { product ->
                when (product.storeName) {
                    "Shopee" -> updateStoreData(
                        R.id.shopee_text,
                        R.id.shopee_stocks,
                        product
                    )
                    "Tokopedia" -> updateStoreData(
                        R.id.tokopedia_text,
                        R.id.tokopedia_stocks,
                        product
                    )
                    "Zalora" -> updateStoreData(
                        R.id.zalora_text,
                        R.id.zalora_stocks,
                        product
                    )
                }
            }
        })

        // Example of inserting a new product
        val shopeeProduct = Product(
            productName = "Shopee Product",
            storeName = "Shopee",
            stock = 100,
            sales = 20,
            date = "2023-01-01"
        )
        productViewModel.insert(shopeeProduct)

        val lazadaProduct = Product(
            productName = "Lazada Product",
            storeName = "Lazada",
            stock = 80,
            sales = 10,
            date = "2023-01-01"
        )
        productViewModel.insert(lazadaProduct)

        val zaloraProduct = Product(
            productName = "Zalora Product",
            storeName = "Zalora",
            stock = 120,
            sales = 15,
            date = "2023-01-01"
        )
        productViewModel.insert(zaloraProduct)

        // Set click listener for the "plus" button in Shopee
        view.findViewById<ImageView>(R.id.shopee_plus).setOnClickListener {
            handlePlusButtonClick("Shopee")
        }

        // Set click listener for the "plus" button in Tokopedia
        view.findViewById<ImageView>(R.id.tokopedia_plus).setOnClickListener {
            handlePlusButtonClick("Tokopedia")
        }

        // Set click listener for the "plus" button in Zalora
        view.findViewById<ImageView>(R.id.zalora_plus).setOnClickListener {
            handlePlusButtonClick("Zalora")
        }
    }

    private fun updateStoreData(storeTextId: Int, stockTextId: Int, product: Product) {
        binding?.let {
            it.root.findViewById<TextView>(storeTextId).text = product.storeName
            it.root.findViewById<TextView>(stockTextId).text = "${product.stock} pcs"
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    private fun handlePlusButtonClick(storeName: String) {
        val currentProduct = productViewModel.allProducts.value?.find { it.storeName == storeName }
        currentProduct?.let { product ->
            val newStock = product.stock?.plus(1) ?: 1
            product.stock = newStock
            productViewModel.update(product)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
