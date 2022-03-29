package com.miftavy.android.inventory.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterBarangKeluar
import com.miftavy.android.inventory.databinding.ActivityMainTambahBarangKeluarBinding
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.model.DataBarangKeluarItem
import com.miftavy.android.inventory.network.Network
import com.miftavy.android.inventory.utils.Constant
import com.pixplicity.easyprefs.library.Prefs
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import kotlin.concurrent.schedule

class MainTambahBarangKeluarActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityMainTambahBarangKeluarBinding
    private lateinit var adapterBarangKeluar: AdapterBarangKeluar
    private lateinit var calendar: Calendar
    private lateinit var dpd: DatePickerDialog
//    private var listJenis = mutableListOf<DataJenisItem?>()
//    private var listBarang = mutableListOf<DataBarangItem?>()
//    private var listUser = mutableListOf<DataUserItem?>()

    private var listkeluar = mutableListOf<DataBarangKeluarItem?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahBarangKeluarBinding.inflate(layoutInflater)

        calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        setContentView(binding.root)
        //set title
        supportActionBar?.title = "Tambah Barang Pinjam"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val incomingData = intent.getParcelableExtra<DataBarangItem?>("data_barang")
//        getListUser()
//        getListJenis()
//        getListBarang()
        //getListBarangKeluar()
        getNextId()

        binding.jenisBarang.setText(incomingData?.jenisBarang)
        binding.kodeBarang.setText(incomingData?.kodeBarang)

//        val pengguna = intent.getParcelableExtra<DataUserItem?>("data_user")
//        binding.pengguna.setText(pengguna?.email)

        binding.pengguna.setText(Prefs.getString(Constant.EMAIL))


        binding.datePicker.setOnClickListener {
            dpd.show(supportFragmentManager, "datePicker")
        }

//

        //adapterBarang = AdapterBarang()
        adapterBarangKeluar = AdapterBarangKeluar() {
            it?.kodeBarangKeluar?.let { it1 ->
                Toast.makeText(
                    this,
                    it1, Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.rvBarangKeluar.apply {
            adapter = adapterBarangKeluar
            layoutManager = LinearLayoutManager(this@MainTambahBarangKeluarActivity)
        }


        // kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener {
//            if (binding.kodeBarangKeluar.text.toString()
//                    .isEmpty() || binding.dropdownkodeBarang.selectedItem.toString().isNotEmpty()
//
//            ) {
//                Toast.makeText(this, "Isi dan pilih semua terlebih dahulu", Toast.LENGTH_SHORT).show()
//                //showMessage("Isi dan pilih semua terlebih dahulu")
//            } else {
//                showLoading()
            val kodeBarangKeluar = binding.kodeBarangKeluar.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val jenisBarang = binding.jenisBarang.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val kodeBarang = binding.kodeBarang.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
//                val jenisBarang = listJenis.find {
//                    it?.jenisBarang == binding.dropdownJenisBarang.selectedItem.toString()
//                }?.jenisBarang.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
//                val kodeBarang = listBarang.find {
//                    it?.kodeBarang == binding.dropdownkodeBarang.selectedItem.toString()
//                }?.kodeBarang.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
            val tanggalKeluar = binding.datePicker.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val jumlah = binding.jumlah.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val pengguna = binding.pengguna.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val alasanPinjem = binding.alasan.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
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
                pengguna,
                alasanPinjem,
                keterangan
            )

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
        pengguna: RequestBody?,
        alasanPinjem: RequestBody,
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
                    pengguna = pengguna,
                    alasanPinjem = alasanPinjem,
                    keterangan = keterangan
                )

                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(
                        this@MainTambahBarangKeluarActivity,
                        "Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik",
                        Toast.LENGTH_SHORT
                    ).show()
                    //showMessage("Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik")
                    Timer(true).schedule(2000) {
                        this@MainTambahBarangKeluarActivity.finish()
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(
                        this@MainTambahBarangKeluarActivity,
                        "Gagal ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //showMessage(e.message.toString())
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            data?.let {
                Log.d("Intent Result", "onActivityResult: $data")
            }
        }
    }


    //    fun addNamaUser(dataUser: List<DataBarangKeluarItem?>?) {
//        val pengguna = mutableListOf<String?>()
//        dataUser?.let { listkeluar.addAll(it) }
//        dataUser?.forEach {
//            pengguna.add(it?.pengguna)
//        }
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pengguna)
//
//        binding.pengguna.adapter = adapter
//    }
//
//    private fun getListBarangKeluar() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = Network().getService().getListBarangKeluar()
//                MainScope().launch {
//                    addNamaUser(response.dataBarangKeluar)
//                }
//            } catch (e: Throwable) {
//                e.printStackTrace()
//            }
//        }
//    }
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (view?.tag == "datePicker") {
            binding.datePicker.text = String.format(
                Locale.getDefault(),
                "%d-%02d-%02d",
                year,
                monthOfYear + 1,
                dayOfMonth
            )
        }
    }

    fun getNextId() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = Network().getService().getNextId()
                //update data ke UI
                MainScope().launch {
                    response.let {
                        Log.d("jhgfds", it.nextid.toString())
                        binding.kodeBarangKeluar.setText(it.nextid.toString())
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                Log.d("GAGAL", "asd")
                MainScope().launch {
                    Toast.makeText(this@MainTambahBarangKeluarActivity, "uji", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        fun makeRequest() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = Network().getService().getListUser()
                    //update data ke UI
//                MainScope().launch {
//                    updateUI(response)
//                }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }


}




