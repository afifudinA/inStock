package com.example.instock.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instock.R
import com.example.instock.databinding.ActivityMainBinding
import com.example.instock.ui.main.fragment.ConsultantFragment
import com.example.instock.ui.main.fragment.DataFragment
import com.example.instock.ui.main.fragment.HomeFragment
import com.example.instock.ui.main.fragment.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val productViewModel: MainViewModel by viewModels()

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_data -> {
                    replaceFragment(DataFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_user -> {
                    replaceFragment(UserFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_data -> {
                    replaceFragment(ConsultantFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set initial fragment
        replaceFragment(HomeFragment())

        // Set up bottom navigation
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
