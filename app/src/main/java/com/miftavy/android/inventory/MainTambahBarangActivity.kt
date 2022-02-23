package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterBarang
import com.miftavy.android.inventory.adapter.Barang
import com.miftavy.android.inventory.databinding.ActivityMainTambahBarangBinding

class MainTambahBarangActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainTambahBarangBinding
    private  lateinit var adapterBarang: AdapterBarang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahBarangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        addJenisBarang()

        //adapterBarang = AdapterBarang()
        adapterBarang = AdapterBarang{
            Toast.makeText(this, it.barang, Toast.LENGTH_SHORT).show()
        }
        binding.rvBarang.apply {
            adapter = adapterBarang
            layoutManager = LinearLayoutManager(this@MainTambahBarangActivity)
        }

        // kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener{
            //ambil inputan text edit text
            val namaBarang = binding.Namabarang.text.toString()
            val hargaBarang = binding.harga.text.toString()
            val jenisBarang = binding.dropdownJenisBarang.selectedItem.toString()

            val barangModel = Barang(barang = namaBarang, harga = hargaBarang, jenis = jenisBarang)
            val listBarangModel = mutableListOf<Barang>()
            listBarangModel.add(barangModel)

            adapterBarang.addItem(listBarangModel)
        }
    }
    fun addJenisBarang(){
        val jenis = mutableListOf("Elektronik", "Berkas")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jenis)

        binding.dropdownJenisBarang.adapter = adapter
    }
}