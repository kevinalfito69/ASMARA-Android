package com.example.asmara.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asmara.R
import com.example.asmara.ui.homepage.MainActivity
import com.example.asmara.ui.offline.OfflineActivity
import com.example.asmara.utils.NetworkUtils

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
//        enableEdgeToEdge()
        // Check if token exists
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        if (!NetworkUtils.isNetworkAvailable(this)) {
            navigateToOfflineActivity()
            return
        }
        if (token != null) {
            // Token exists, redirect to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("TOKEN", token)
            startActivity(intent)
            finish()
        }else {
            setContentView(R.layout.activity_auth)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
    private fun navigateToOfflineActivity() {
        val intent = Intent(this, OfflineActivity::class.java)
        startActivity(intent)
        finish() // Optional: Finish MainActivity so user cannot go back to it
    }
}