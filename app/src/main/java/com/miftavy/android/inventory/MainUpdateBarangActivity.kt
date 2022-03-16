package com.miftavy.android.inventory

import android.R
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import com.miftavy.android.inventory.databinding.ActivityMainUpdateBarangBinding
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.model.DataJenisItem
import com.miftavy.android.inventory.model.DataSupplierItem
import com.miftavy.android.inventory.network.Network
import com.miftavy.android.inventory.utils.FileUtils
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.concurrent.schedule

class MainUpdateBarangActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener ,BottomSheetImagePicker.OnImagesSelectedListener {
    private lateinit var binding: ActivityMainUpdateBarangBinding
    private var listItem: MutableList<DataJenisItem?>? = mutableListOf()
    private lateinit var adapter: ArrayAdapter<String?>
    private lateinit var calendar: Calendar
    private lateinit var photo: MultipartBody.Part
    private lateinit var dpd: DatePickerDialog
    private var sudahPilihGambar = false
    private var listJenis = mutableListOf<DataJenisItem?>()
    private var listSupplier = mutableListOf<DataSupplierItem?>()
    private var listKondisi = mutableListOf<String>("Baik", "Bekas","Rusak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainUpdateBarangBinding.inflate(layoutInflater)

        calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR)
            , calendar.get(Calendar.MONTH)
            , calendar.get(Calendar.DAY_OF_MONTH))

        setContentView(binding.root)

        //set title
        supportActionBar?.title = "Update Barang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val incomingData = intent.getParcelableExtra<DataBarangItem?>("data_barang")

        getListJenis(incomingData)
        addKondisiBarang()
        getListJenis()
        getListSupplier()
        binding.datePicker.setOnClickListener {
            dpd.show(supportFragmentManager, "datePicker")
        }
        binding.pilihGambar.setOnClickListener {
            BottomSheetImagePicker.Builder(getString(com.miftavy.android.inventory.R.string.app_name))
                .singleSelectTitle(com.miftavy.android.inventory.R.string.pick_one)
                .cameraButton(ButtonType.Tile)
                .galleryButton(ButtonType.Tile)
                .build()
                .show(supportFragmentManager, "picker")
        }

        binding.updateBarang.setOnClickListener {
            val kodeBarang = binding.kodeBarang.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val namaBarang = binding.namaBarang.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val kondisi = listKondisi.find {
                it == binding.dropdownkondisi.selectedItem.toString()
            }?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
            val jenisBarang = listJenis.find {
                it?.jenisBarang == binding.dropdownJenisBarang.selectedItem.toString()
            }?.idJenisBarang?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
            val hargaBeli = binding.hargaBeli.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val merek = binding.merek.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val satuan = binding.satuan.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val jumlahBeli = binding.jumlahBeli.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val namaSupplier = listSupplier.find {
                it?.namaSupplier == binding.dropdownNamasupplier.selectedItem.toString()
            }?.namaSupplier?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
            val tanggalMasuk = binding.datePicker.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())

//            Toast.makeText(this, jenisBarang.toString(), Toast.LENGTH_SHORT).show()
            if (!sudahPilihGambar)
                photo = MultipartBody.Part.createFormData("gambar", "")

            sendData(

                kodeBarang,
                namaBarang,
                kondisi,
                jenisBarang,
                hargaBeli,
                merek,
                satuan,
                jumlahBeli,
                namaSupplier,
                tanggalMasuk,
                photo
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
        kodeBarang: RequestBody,
        namaBarang: RequestBody,
        kondisi: RequestBody?,
        jenisBarang: RequestBody?,
        hargaBeli: RequestBody,
        merek: RequestBody,
        satuan: RequestBody,
        jumlahBeli: RequestBody,
        namaSupplier: RequestBody?,
        tanggalMasuk: RequestBody?,
        photo: MultipartBody.Part
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = Network().getService().updateBarang(
                    kodeBarang = kodeBarang,
                    namaBarang = namaBarang,
                    kondisi = kondisi,
                    jenisBarang = jenisBarang,
                    hargaBeli = hargaBeli,
                    merek = merek,
                    satuan = satuan,
                    jumlahBeli = jumlahBeli,
                    namaSupplier = namaSupplier,
                    tanggalMasuk = tanggalMasuk,
                    gambar = photo
                )
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(
                        this@MainUpdateBarangActivity,
                        "Data berhasil diubah. Halaman ini akan ditutup dalam 2 detik",
                        Toast.LENGTH_SHORT
                    ).show()
                    //showMessage("Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik")
                    Timer(true).schedule(2000) {
                        this@MainUpdateBarangActivity.finish()
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(this@MainUpdateBarangActivity, "Gagal ", Toast.LENGTH_SHORT)
                        .show()
                    //showMessage(e.message.toString())
                }
            }
        }
    }
    private fun setToUI(incomingData: DataBarangItem?) {
        binding.kodeBarang.setText(incomingData?.kodeBarang)
        binding.merek.setText(incomingData?.merek)
        binding.datePicker.text = incomingData?.tanggalMasuk
        binding.hargaBeli.setText(incomingData?.hargaBeli.toString())
        binding.namaBarang.setText(incomingData?.namaBarang)
        binding.jumlahBeli.setText(incomingData?.jumlahBeli)
        binding.satuan.setText(incomingData?.satuan)


        if (incomingData?.jenisBarang != null) {
            val spinnerPosition: Int = adapter.getPosition(incomingData.jenisBarang)
            binding.dropdownJenisBarang.setSelection(spinnerPosition)
        }
        if (incomingData?.namaSupplier != null) {
            val spinnerPosition: Int = adapter.getPosition(incomingData.namaSupplier)
            binding.dropdownNamasupplier.setSelection(spinnerPosition)
        }
        if (incomingData?.kondisi != null) {
            val spinnerPosition: Int = adapter.getPosition(incomingData.kondisi)
            binding.dropdownkondisi.setSelection(spinnerPosition)
        }
//
    }

    private fun getListJenis(incomingData: DataBarangItem?) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListJenis()
                MainScope().launch {
                    addJenisBarang(response.dataJenis, incomingData)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            } finally {
//                setToUI(incomingData)
            }
        }
    }



    fun addJenisBarang(dataJenis: List<DataJenisItem?>?, incomingData: DataBarangItem?) {
        val jenis = mutableListOf<String?>()
        dataJenis?.let { listJenis.addAll(it) }
        dataJenis?.forEach {
            jenis.add(it?.jenisBarang)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jenis)

        binding.dropdownJenisBarang.adapter = adapter

        try {
            setToUI(incomingData)

        }catch (e: Throwable){
            e.printStackTrace()
        }
    }
    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        if (uris.isNotEmpty()) {
            val uri = uris.first()
            val file = File(FileUtils.getPath(this, uri))

            //decode file ke bmp
            val bmp = BitmapFactory.decodeFile(file.path)

            //convert ke byte array
            val byteArrayOutputStream = ByteArrayOutputStream()
            //compress ke JPG
            bmp.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream)

            //create temp body
            val tempPhoto = byteArrayOutputStream.toByteArray()
                .toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArrayOutputStream.size())

            photo = MultipartBody.Part.createFormData("gambar", file.name, tempPhoto)


            //ubah nilai pilih gambar ke true
            sudahPilihGambar = true
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
    fun addNamaSupplier(dataSupplier: List<DataSupplierItem?>?) {
        val Supplier = mutableListOf<String?>()
        dataSupplier?.let { listSupplier.addAll(it) }
        dataSupplier?.forEach {
            Supplier.add(it?.namaSupplier)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Supplier)

        binding.dropdownNamasupplier.adapter = adapter
    }

    private fun getListSupplier() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = Network().getService().getListSupplier()
                MainScope().launch {
                    addNamaSupplier(response.dataSupplier)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
    fun addKondisiBarang(){
        val jenis = mutableListOf("Baik", "Bekas","Rusak")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jenis)

        binding.dropdownkondisi.adapter = adapter
    }
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if(view?.tag == "datePicker"){
            binding.datePicker.text = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear+1, dayOfMonth)
        }
    }

}