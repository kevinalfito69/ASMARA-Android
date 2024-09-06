package com.example.asmara.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.asmara.R
import com.example.asmara.databinding.ActivityMainBinding
import com.example.asmara.ui.offline.OfflineActivity
import com.example.asmara.utils.NetworkUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the Toolbar as the support action bar
        setSupportActionBar(binding.toolbar)
        if (!NetworkUtils.isNetworkAvailable(this)) {
            navigateToOfflineActivity()
            return
        }
        // Verify if the action bar is set
        supportActionBar ?: throw IllegalStateException("ActionBar not set")
        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Set up the app bar configuration with top-level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_profile)
        )

        // Set up the navigation controller with the app bar and bottom navigation view
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> showBottomNav()
                R.id.navigation_profile -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun navigateToOfflineActivity() {
        val intent = Intent(this, OfflineActivity::class.java)
        startActivity(intent)
        finish() // Optional: Finish MainActivity so user cannot go back to it
    }

}
