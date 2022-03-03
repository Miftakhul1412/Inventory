package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterStok
import com.miftavy.android.inventory.databinding.ActivityMainTambahStokBinding
import com.miftavy.android.inventory.model.DataStokItem


class MainTambahStokActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainTambahStokBinding
    private  lateinit var adapterStok: AdapterStok

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahStokBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //adapterBarang = AdapterBarang()
        adapterStok = AdapterStok{
            Toast.makeText(this, it?.kodeBarang, Toast.LENGTH_SHORT).show()
        }
        binding.rvStok.apply {
            adapter = adapterStok
            layoutManager = LinearLayoutManager(this@MainTambahStokActivity)
        }
// kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener{
            //ambil inputan text edit text
            val kodeBarang = binding.kodeBarang.text.toString()
            val kodeStok = binding.kodeStok.text.toString()
            val batasMin = binding.batasMin.text.toString()
            val stok = binding.stok.text.toString()

            val StokModel = DataStokItem(kodeBarang = kodeBarang, kodeStok = kodeStok.toInt(), batasMin = batasMin.toInt(), stok = stok.toInt())
            val listStokModel = mutableListOf<DataStokItem?>()
            listStokModel.add(StokModel)

            adapterStok.addItem(listStokModel)
        }
    }
    fun addJenisBarang(){
    }
}