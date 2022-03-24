package com.miftavy.android.inventory.user

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterBarangKeluar
import com.miftavy.android.inventory.databinding.ActivityMainTambahBarangKeluarBinding
import com.miftavy.android.inventory.model.*
import com.miftavy.android.inventory.network.Network
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import kotlin.concurrent.schedule

class MainTambahBarangKeluarActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding : ActivityMainTambahBarangKeluarBinding
    private  lateinit var adapterBarangKeluar: AdapterBarangKeluar
    private lateinit var calendar: Calendar
    private lateinit var dpd: DatePickerDialog
    private var listJenis = mutableListOf<DataJenisItem?>()
    private var listBarang = mutableListOf<DataBarangItem?>()
    private var listUser = mutableListOf<DataUserItem?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahBarangKeluarBinding.inflate(layoutInflater)

        calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR)
            , calendar.get(Calendar.MONTH)
            , calendar.get(Calendar.DAY_OF_MONTH))

        setContentView(binding.root)
        //set title
        supportActionBar?.title = "Tambah Barang Pinjam"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getListUser()
        getListJenis()
        getListBarang()


        binding.datePicker.setOnClickListener {
            dpd.show(supportFragmentManager, "datePicker")
        }

        binding.dropdownJenisBarang?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
//            binding.dropdownNamaSupplier?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var jenis= listJenis.get(position)
                var lastkode = if (jenis?.lastId == null) "0001" else "0000"+(jenis?.lastId!!.toInt() + 1).toString();
                val dropdownkodeBarang = jenis?.kodejenis + "-LTI-" + lastkode.substring(lastkode.length - 4, lastkode.length)
                binding.dropdownkodeBarang.selectedItem(dropdownkodeBarang)
            }
        }


        //adapterBarang = AdapterBarang()
        adapterBarangKeluar = AdapterBarangKeluar(){
            it?.kodeBarangKeluar?.let { it1 ->
                Toast.makeText(this,
                    it1, Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvBarangKeluar.apply {
            adapter = adapterBarangKeluar
            layoutManager = LinearLayoutManager(this@MainTambahBarangKeluarActivity)
        }


        // kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener {
            if (binding.kodeBarangKeluar.text.toString()
                    .isEmpty() || binding.dropdownkodeBarang.selectedItem.toString().isNotEmpty()

            ) {
                Toast.makeText(this, "Isi dan pilih semua terlebih dahulu", Toast.LENGTH_SHORT).show()
                //showMessage("Isi dan pilih semua terlebih dahulu")
            } else {
//                showLoading()
                val kodeBarangKeluar = binding.kodeBarangKeluar.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val jenisBarang = listJenis.find {
                    it?.jenisBarang == binding.dropdownJenisBarang.selectedItem.toString()
                }?.jenisBarang.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
                val kodeBarang = listBarang.find {
                    it?.kodeBarang == binding.dropdownkodeBarang.selectedItem.toString()
                }?.kodeBarang.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
                val tanggalKeluar = binding.datePicker.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val jumlah = binding.jumlah.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val name = listUser.find {
                    it?.name == binding.dropdownPengguna.selectedItem.toString()
                }?.name?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
                val keterangan = binding.keterangan.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())

//                Toast.makeText(this, kondisi.toString(), Toast.LENGTH_SHORT).show()
//         Toast.makeText(this, namaSupplier.toString(), Toast.LENGTH_SHORT).show()

                sendData(

                    kodeBarangKeluar,
                    jenisBarang,
                    kodeBarang,
                    tanggalKeluar,
                    jumlah,
                    name,
                    keterangan
                )

            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()

        }
        return super.onOptionsItemSelected(item)
    }
    private fun sendData(
        kodeBarangKeluar: RequestBody,
        jenisBarang: RequestBody?,
        kodeBarang: RequestBody?,
        tanggalKeluar: RequestBody,
        jumlah: RequestBody,
        name: RequestBody?,
        keterangan: RequestBody
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = Network().getService().tambahBarangKeluar(
                        kodeBarangKeluar = kodeBarangKeluar,
                        jenisBarang = jenisBarang,
                        kodeBarang = kodeBarang,
                        tanggalKeluar = tanggalKeluar,
                        jumlah = jumlah,
                        name = name,
                        keterangan = keterangan
                    )

                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(this@MainTambahBarangKeluarActivity, "Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik", Toast.LENGTH_SHORT).show()
                    //showMessage("Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik")
                    Timer(true).schedule(2000) {
                        this@MainTambahBarangKeluarActivity.finish()
                    }
                }
        } catch (e: Throwable) {
            e.printStackTrace()
            MainScope().launch {
//                    dismissLoading()
                Toast.makeText(this@MainTambahBarangKeluarActivity, "Gagal ", Toast.LENGTH_SHORT).show()
                //showMessage(e.message.toString())
            }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){
            data?.let {
                Log.d("Intent Result", "onActivityResult: $data")
            }
        }
    }
    fun addJenisBarang(dataJenis: List<DataJenisItem?>?) {
        val jenis = mutableListOf<String?>()
        dataJenis?.let { listJenis.addAll(it) }
        dataJenis?.forEach {
            jenis.add(it?.jenisBarang)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jenis)

        binding.dropdownJenisBarang.adapter = adapter
    }

    private fun getListJenis(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListJenis()
                MainScope().launch {
                    addJenisBarang(response.dataJenis)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    //
    fun addKodeBarang(dataBarang: List<DataBarangItem?>?) {
        val kodeBarang = mutableListOf<String?>()
        dataBarang?.let { listBarang.addAll(it) }
        dataBarang?.forEach {
            kodeBarang.add(it?.kodeBarang)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, kodeBarang)

        binding.dropdownkodeBarang.adapter = adapter
    }

    private fun getListBarang() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = Network().getService().getListBarang()
                MainScope().launch {
                    addKodeBarang(response.dataBarang)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun addNamaUser(dataUser: List<DataUserItem?>?) {
        val namaUser = mutableListOf<String?>()
        dataUser?.let { listUser.addAll(it) }
        dataUser?.forEach {
            namaUser.add(it?.name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, namaUser)

        binding.dropdownPengguna.adapter = adapter
    }

    private fun getListUser() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = Network().getService().getListUser()
                MainScope().launch {
                    addNamaUser(response.dataUser)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if(view?.tag == "datePicker"){
            binding.datePicker.text = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear+1, dayOfMonth)
        }
    }

}

private fun Spinner.selectedItem(dropdownkodeBarang: String) {


}
