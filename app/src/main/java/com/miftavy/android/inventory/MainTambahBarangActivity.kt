package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterBarang
import com.miftavy.android.inventory.adapter.Barang
import com.miftavy.android.inventory.databinding.ActivityMainTambahBarangBinding
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class MainTambahBarangActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding : ActivityMainTambahBarangBinding
    private  lateinit var adapterBarang: AdapterBarang
    private lateinit var calendar: Calendar
    private lateinit var dpd: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahBarangBinding.inflate(layoutInflater)

        calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR)
            , calendar.get(Calendar.MONTH)
            , calendar.get(Calendar.DAY_OF_MONTH))

        setContentView(binding.root)

        addJenisBarang()


        binding.datePicker.setOnClickListener {
            dpd.show(supportFragmentManager, "datePicker")
        }

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

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if(view?.tag == "datePicker"){
            binding.datePicker.text = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear+1, dayOfMonth)
        }
    }

}