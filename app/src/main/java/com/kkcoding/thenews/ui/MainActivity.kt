package com.kkcoding.thenews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.kkcoding.thenews.R
import com.kkcoding.thenews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController.apply {
            binding.bottomNavigationView.setupWithNavController(this)
        }

        navController = navHostFragment.navController

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.pageTitleBar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)
        NavigationUI.setupActionBarWithNavController(
            this,
            navHostFragment.navController,
            appBarConfiguration
        )

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->

            binding.bottomNavigationView.isVisible = destination.id != R.id.newsDetailsFragment
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}