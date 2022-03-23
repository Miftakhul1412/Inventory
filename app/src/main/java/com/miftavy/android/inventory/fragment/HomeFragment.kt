package com.miftavy.android.inventory.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.databinding.FragmentHomeBinding

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
//
    //ini dipanggil ketika layout itu sudah tampil
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)



//        val listPengumuman = mutableListOf<PengumumanItem>()
//        val pengumumanItem1 = PengumumanItem()
//        pengumumanItem1.gambar = R.drawable.ic_home
//        pengumumanItem1.judul = "Contoh Judul Pengumuman"
//        pengumumanItem1.lampiran = "Contoh Lampiran Pengumuman"
//
//        listPengumuman.add(pengumumanItem1)
//
//        val pengumumanItem2 = PengumumanItem()
//        pengumumanItem2.gambar = R.drawable.ic_home
//        pengumumanItem2.judul = "Pengumuman 2"
//        pengumumanItem2.lampiran = "Contoh Pengumuman 2"
//
//        listPengumuman.add(pengumumanItem2)
//
//        adapterPengumuman = AdapterPengumuman(onItemClick = {
//            Intent(requireActivity(), DetailPengumuman::class.java).apply {
//                putExtra("item_pengumuman", it)
//                startActivity(this)
//            }
//        })
//        binding.rvPengumuman.adapter = adapterPengumuman
//        binding.rvPengumuman.layoutManager = LinearLayoutManager(requireActivity())
//
//        adapterPengumuman.addItem(listPengumuman)

//        binding.pkp.setOnClickListener {
//            Intent(requireActivity(), PkpActivity::class.java).apply {
//                startActivity(this)
//            }
//        }
//
//        binding.monitoring.setOnClickListener {
//            Intent(requireActivity(), MonitoringActivity::class.java).apply {
//                startActivity(this)
//            }
//        }
    }
}