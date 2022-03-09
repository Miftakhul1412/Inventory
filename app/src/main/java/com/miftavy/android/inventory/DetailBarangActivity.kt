package com.miftavy.android.inventory

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.network.Network

private const val ARG_PARAM1 = "barangItem"

class DetailBarangActivity : BottomSheetDialogFragment() {

    private var barangItem: DataBarangItem? = null
    private var behavior: BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            barangItem = it.getParcelable(ARG_PARAM1)
        }
    }
//        setContentView(R.layout.activity_detail_barang)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val bottomSheets = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.activity_detail_barang, null)
        val root = view.findViewById<LinearLayout>(R.id.root)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        root.layoutParams = params
        bottomSheets.setContentView(view)
        behavior = BottomSheetBehavior.from(view.parent as View)

        // prevent from dragging
        bottomSheets.setOnShowListener {
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }

        initViews(view)

        return bottomSheets
    }

    private fun initViews(view: View) {
        view.findViewById<TextView>(R.id.namaBarang)?.text = barangItem?.namaBarang
        view.findViewById<TextView>(R.id.hargaBeli)?.text = barangItem?.hargaBeli.toString()
        view.findViewById<TextView>(R.id.jenisBarang)?.text = barangItem?.jenisBarang
        Glide.with(requireActivity())
            .load("${Network().BASE_URL}${barangItem?.gambar}")
//            .load("${NetModule.BASEURL}assets/foto/${alumniItem?.foto}")
            .circleCrop()
            .into(view.findViewById(R.id.gambar))

        view.findViewById<Button>(R.id.updateBarang).setOnClickListener {
            Intent(requireActivity(), MainUpdateBarangActivity::class.java).apply {
                putExtra("data_barang", barangItem)
                startActivity(this)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(barangItem: DataBarangItem?) =
            DetailBarangActivity().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, barangItem)
                }
            }
    }

}