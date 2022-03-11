package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.miftavy.android.inventory.databinding.ActivityResultScanQrBinding

class ResultScanQR : AppCompatActivity() {

    lateinit var binding : ActivityResultScanQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set title
        supportActionBar?.title = "Result Scan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}