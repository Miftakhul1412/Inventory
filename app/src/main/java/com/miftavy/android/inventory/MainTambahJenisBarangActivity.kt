package com.miftavy.android.inventory

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.*
import com.miftavy.android.inventory.databinding.ActivityMainTambahBarangBinding
import com.miftavy.android.inventory.databinding.ActivityMainTambahJenisBarangBinding
import com.miftavy.android.inventory.databinding.ActivityMainTambahStokBinding

class MainTambahJenisBarangActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainTambahJenisBarangBinding
    private  lateinit var adapterJenis: AdapterJenis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahJenisBarangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //adapterBarang = AdapterBarang()
        adapterJenis = AdapterJenis{
            Toast.makeText(this, it.jenisBarang, Toast.LENGTH_SHORT).show()
        }
        binding.rvJenis.apply {
            adapter = adapterJenis
            layoutManager = LinearLayoutManager(this@MainTambahJenisBarangActivity)
        }
// kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener{
            //ambil inputan text edit text
            val idjenis = binding.idJenis.text.toString()
            val jenisBarang = binding.jenisBarang.text.toString()
            val JenisModel = Jenis(idJenis = idjenis, jenisBarang = jenisBarang)
            val listJenisBarangModel = mutableListOf<Jenis>()
            listJenisBarangModel.add(JenisModel)

            adapterJenis.addItem(listJenisBarangModel)
        }
    }
    fun addJenisBarang(){
    }
}