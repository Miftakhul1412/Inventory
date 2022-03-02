package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterStok
import com.miftavy.android.inventory.adapter.Stok
import com.miftavy.android.inventory.databinding.ActivityMainTambahStokBinding


class MainTambahStokActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainTambahStokBinding
    private  lateinit var adapterStok: AdapterStok

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahStokBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //adapterBarang = AdapterBarang()
        adapterStok = AdapterStok{
            Toast.makeText(this, it.stok, Toast.LENGTH_SHORT).show()
        }
        binding.rvStok.apply {
            adapter = adapterStok
            layoutManager = LinearLayoutManager(this@MainTambahStokActivity)
        }
// kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener{
            //ambil inputan text edit text
            val kodeBarang = binding.kodeBarang.text.toString()
            val namaBarang = binding.NamaBarang.text.toString()
            val batasMin = binding.batasMin.text.toString()
            val stokBarang = binding.stok.text.toString()

            val StokModel = Stok(kode = kodeBarang, barang = namaBarang, batasMin = batasMin, stok = stokBarang)
            val listStokModel = mutableListOf<Stok>()
            listStokModel.add(StokModel)

            adapterStok.addItem(listStokModel)
        }
    }
    fun addJenisBarang(){
    }
}