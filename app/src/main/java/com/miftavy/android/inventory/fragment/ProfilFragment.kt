package com.miftavy.android.inventory.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.databinding.FragmentProfilBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilFragment : Fragment(R.layout.fragment_profil) {
    private lateinit var binding: FragmentProfilBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfilBinding.bind(view)

//        binding.namauser.text = Prefs.getString(Constant.NAMA)
//        binding.userid.text = Prefs.getString(Constant.USERID)
//
//        binding.logout.setOnClickListener {
//            Prefs.clear()
//            Intent(requireActivity(), MainLogin::class.java).apply {
//                startActivity(this)
//            }
//            requireActivity().finishAffinity()
//        }
    }
}