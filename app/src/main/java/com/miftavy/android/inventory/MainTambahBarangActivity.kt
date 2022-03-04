package com.miftavy.android.inventory

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import com.miftavy.android.inventory.adapter.AdapterBarang
import com.miftavy.android.inventory.databinding.ActivityMainTambahBarangBinding
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

class MainTambahBarangActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener ,BottomSheetImagePicker.OnImagesSelectedListener {
    private lateinit var binding : ActivityMainTambahBarangBinding
    private  lateinit var adapterBarang: AdapterBarang
    private lateinit var calendar: Calendar
    private lateinit var dpd: DatePickerDialog
    private lateinit var photo: MultipartBody.Part
    private var sudahPilihGambar = false
    private var listJenis = mutableListOf<DataJenisItem?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahBarangBinding.inflate(layoutInflater)

        calendar = Calendar.getInstance()
        dpd = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR)
            , calendar.get(Calendar.MONTH)
            , calendar.get(Calendar.DAY_OF_MONTH))

        setContentView(binding.root)

        getListJenis()


        binding.datePicker.setOnClickListener {
            dpd.show(supportFragmentManager, "datePicker")
        }

        //adapterBarang = AdapterBarang()
        adapterBarang = AdapterBarang{
            Toast.makeText(this, it?.namaBarang, Toast.LENGTH_SHORT).show()
        }
        binding.rvBarang.apply {
            adapter = adapterBarang
            layoutManager = LinearLayoutManager(this@MainTambahBarangActivity)
        }

        binding.pilihGambar.setOnClickListener {
            BottomSheetImagePicker.Builder(getString(R.string.app_name))
                .singleSelectTitle(R.string.pick_one)
                .cameraButton(ButtonType.Tile)
                .galleryButton(ButtonType.Tile)
                .build()
                .show(supportFragmentManager, "picker")
        }

        // kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener {
            if (binding.namaBarang.text.toString()
                    .isEmpty() || binding.dropdownJenisBarang.selectedItem.toString()
                    .isEmpty() || !sudahPilihGambar
            ) {
                Toast.makeText(this, "Isi dan pilih semua terlebih dahulu", Toast.LENGTH_SHORT).show()
                //showMessage("Isi dan pilih semua terlebih dahulu")
            } else {
//                showLoading()
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

                Toast.makeText(this, "$jenisBarang", Toast.LENGTH_SHORT).show()

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
                val res = Network().getService().tambahBarang(
                    kodeBarang = kodeBarang,
                    namaBarang = namaBarang,
                    jenisBarang = jenisBarang,
                    hargaBeli = hargaBeli,
                    tanggalMasuk = tanggalMasuk,
                    gambar = photo
                )
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(this@MainTambahBarangActivity, "Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik", Toast.LENGTH_SHORT).show()
                    //showMessage("Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik")
                    Timer(true).schedule(2000) {
                        this@MainTambahBarangActivity.finish()
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(this@MainTambahBarangActivity, "coba", Toast.LENGTH_SHORT).show()
                    //showMessage(e.message.toString())
                }
            }
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

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if(view?.tag == "datePicker"){
            binding.datePicker.text = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear+1, dayOfMonth)
        }
    }

}