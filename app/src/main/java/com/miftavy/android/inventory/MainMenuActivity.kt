package com.miftavy.android.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miftavy.android.inventory.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    //deklarasi variabel tanpa ngasi nilai awal
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.tambahBarang.setOnClickListener {
                Intent(this@MainMenuActivity, MainTambahBarangActivity::class.java).apply {
                    startActivity(this)
                }
            }
            binding.tambahStok.setOnClickListener {
                Intent(this@MainMenuActivity, MainTambahStokActivity::class.java).apply {
                    startActivity(this)
                }
            }
    }
}