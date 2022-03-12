package com.miftavy.android.inventory

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import com.miftavy.android.inventory.databinding.ActivityMainUpdateBarangBinding
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.model.DataJenisItem
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
            val jenisBarang = listJenis.find {
                it?.jenisBarang == binding.dropdownJenisBarang.selectedItem.toString()
            }?.idJenisBarang?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
            val hargaBeli = binding.hargaBeli.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val tanggalMasuk = binding.datePicker.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())

            Toast.makeText(this, listJenis.toString(), Toast.LENGTH_SHORT).show()

            sendData(

                kodeBarang,
                namaBarang,
                jenisBarang,
                hargaBeli,
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
        jenisBarang: RequestBody?,
        hargaBeli: RequestBody,
        tanggalMasuk: RequestBody?,
        photo: MultipartBody.Part
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = Network().getService().updateBarang(
                    kodeBarang = kodeBarang,
                    namaBarang = namaBarang,
                    jenisBarang = jenisBarang,
                    hargaBeli = hargaBeli,
                    tanggalMasuk = tanggalMasuk,
                    gambar = photo
                )
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(
                        this@MainUpdateBarangActivity,
                        "Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik",
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
        binding.datePicker.text = incomingData?.tanggalMasuk
        binding.hargaBeli.setText(incomingData?.hargaBeli.toString())
        binding.namaBarang.setText(incomingData?.namaBarang)
        if (incomingData?.jenisBarang != null) {
            val spinnerPosition: Int = adapter.getPosition(incomingData.jenisBarang)
            binding.dropdownJenisBarang.setSelection(spinnerPosition)
        }
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

//    fun addJenisBarang(dataJenis: List<DataJenisItem?>?, incomingData: DataBarangItem?) {
//       val jenis = mutableListOf<String?>()
//        dataJenis?.let { listItem?.addAll(it) }
//        dataJenis?.forEach {
//            jenis.add(it?.jenisBarang)
//        }
//        var  adapter = ArrayAdapter(this, R.layout.simple_list_item_1, jenis)
//
//        binding.dropdownJenisBarang.adapter = adapter
//
//
//        setToUI(incomingData)
//
//    }

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

//    fun addJenisBarang(dataJenis: List<DataJenisItem?>?, incomingData: DataBarangItem?) {
//        val jenis = mutableListOf<String?>()
//        dataJenis?.let { listJenis.addAll(it) }
//        dataJenis?.forEach {
//            jenis.add(it?.jenisBarang)
//        }
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jenis)
//
//        binding.dropdownJenisBarang.adapter = adapter
//    }
//
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

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if(view?.tag == "datePicker"){
            binding.datePicker.text = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear+1, dayOfMonth)
        }
    }

}