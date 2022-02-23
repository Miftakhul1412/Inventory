package com.miftavy.android.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.miftavy.android.inventory.databinding.ActivityMainBinding
import com.miftavy.android.inventory.databinding.ActivityMainLoginBinding

class MainLogin : AppCompatActivity() {
    private lateinit var binding: ActivityMainLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.login.setOnClickListener {
            Intent(this@MainLogin, MainActivity::class.java).apply {
                startActivity(this)
            }

        }
    }
}