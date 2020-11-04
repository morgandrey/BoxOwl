package com.example.boxowl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.boxowl.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}