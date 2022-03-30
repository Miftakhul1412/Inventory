package com.miftavy.android.inventory.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.DetailBarangKeluarActivity
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.adapter.AdapterBarang
import com.miftavy.android.inventory.adapter.AdapterBarangKeluar
import com.miftavy.android.inventory.databinding.FragmentHomeBinding
import com.miftavy.android.inventory.network.Network
import com.miftavy.android.inventory.utils.Constant
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.reflect.Array.set

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
//    private lateinit var adapterPengumuman: AdapterPengumuman
//    lateinit var adapterpeng : AdapterPengumumanAll
private lateinit var binding: FragmentHomeBinding
private lateinit var adapterBarangKeluar: AdapterBarangKeluar
//
    //ini dipanggil ketika layout itu sudah tampil
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

    //initialize adapter barang
    adapterBarangKeluar = AdapterBarangKeluar(AdapterBarang.LINEARTYPE) {
        val showDialogDetailBarangKeluar = DetailBarangKeluarActivity.newInstance(it)
        showDialogDetailBarangKeluar.show(showDialogDetailBarangKeluar, "detail")
    }

    binding.rvListBarangKeluarByUser.apply {
        adapter = adapterBarangKeluar
        layoutManager = LinearLayoutManager(activity)
    }

    makeRequest()

    }



    fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListBarangKeluarByUser(
                    Prefs.getString(
                        Constant.EMAIL),5)
                //update data ke UI
                MainScope().launch {
                    response.dataBarangKeluarByUser?.let { adapterBarangKeluar.addItem(it, true)
                    }
                }
            }catch (e: Throwable){
                e.printStackTrace()
                Log.d("GAGAL", "asd")
                MainScope().launch {
                    Toast.makeText(requireContext(),"UJI", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

private fun DetailBarangKeluarActivity.show(showDialogDetailBarangKeluar: DetailBarangKeluarActivity, s: String) {

}


