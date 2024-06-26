package com.example.instock.ui.insert

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.instock.R
import com.example.instock.database.Product
import com.example.instock.databinding.ActivityProductAddUpdateBinding
import com.example.instock.helper.DateHelper
import com.example.instock.helper.ViewModelFactory

class ProductAddUpdateActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var isEdit = false
    private var product: Product? = null

    private lateinit var productAddUpdateViewModel: ProductAddUpdateViewModel
    private var _activityProductAddUpdateBinding: ActivityProductAddUpdateBinding? = null
    private val binding get() = _activityProductAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityProductAddUpdateBinding = ActivityProductAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        productAddUpdateViewModel = obtainViewModel(this@ProductAddUpdateActivity)

        // Check if intent has EXTRA_NOTE for editing
        if (intent.hasExtra(EXTRA_NOTE)) {
            isEdit = true
            product = intent.getParcelableExtra(EXTRA_NOTE)
            product?.let {
                binding?.edtTitle?.setText(it.productName)
                binding?.edtDescription?.setText(it.storeName)
                // Populate other fields as needed
            }
        }

        // Initialize UI components and ViewModel
        initializeUI()
    }

    private fun initializeUI() {
        // Set up UI components and listeners
        // For example, setting up click listener for submit button
        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()

            if (product == null) {
                product = Product(
                    productName = title,
                    storeName = description,
                    sales = 1,
                    stock = 1,
                    date = "Sekarang"
                )
            } else {
                product?.productName = title
                product?.storeName = description
                product?.sales = 1
                product?.stock = 1
                product?.date = "Sekarang"
            }

            if (isEdit) {
                productAddUpdateViewModel.update(product as Product)
                showToast(getString(R.string.changed))
            } else {
                product?.date = DateHelper.getCurrentDate()
                productAddUpdateViewModel.insert(product as Product)
                showToast(getString(R.string.added))
            }
            finish()
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    productAddUpdateViewModel.delete(product as Product)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityProductAddUpdateBinding = null
    }
    private fun obtainViewModel(activity: AppCompatActivity): ProductAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ProductAddUpdateViewModel::class.java)
    }
}