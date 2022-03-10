package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miftavy.android.inventory.databinding.ActivityResultScanQrBinding

class ResultScanQR : AppCompatActivity() {

    lateinit var binding : ActivityResultScanQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanQrBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}