package com.miftavy.android.inventory.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.miftavy.android.inventory.MainLogin
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.databinding.FragmentProfilBinding
import com.miftavy.android.inventory.model.ResponseListUser
import com.miftavy.android.inventory.network.Network
import com.miftavy.android.inventory.utils.Constant
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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

        binding.namauser.text = Prefs.getString(Constant.NAMA)
        binding.email.text = Prefs.getString(Constant.EMAIL)
        binding.level.text = Prefs.getString(Constant.LEVEL)


        binding.logout.setOnClickListener {
            Prefs.clear()
            Intent(requireActivity(), MainLogin::class.java).apply {
                startActivity(this)
            }
            requireActivity().finishAffinity()
        }

        makeRequest()
    }


    private fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListUser()
                //update data ke UI
//                MainScope().launch {
//                    updateUI(response)
//                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

//    private fun updateUI(response: ResponseListUser) {
//        response.dataUser?.let {
//            adapterUser.addItem(it)
//        }
//    }
}