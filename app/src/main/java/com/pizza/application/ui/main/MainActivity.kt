package com.pizza.application.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pizza.application.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    companion object {
        private val TOOLBAR_DESTINATION = setOf(
            R.id.navigation_menu,
            R.id.navigation_profile,
            R.id.navigation_shopping_cart
        )
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavigationHostFragment) as? NavHostFragment
            ?: return
        navController = findNavController(R.id.mainNavigationHostFragment)
        appBarConfiguration = AppBarConfiguration(TOOLBAR_DESTINATION)

        val mainBottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)

        navController?.apply {
            mainBottomNavigationView.setupWithNavController(this)
        }
    }
}