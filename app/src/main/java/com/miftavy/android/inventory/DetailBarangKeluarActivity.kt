package com.miftavy.android.inventory

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.miftavy.android.inventory.model.DataBarangKeluarItem

private const val ARG_PARAM1 = "barangkeluar"

class DetailBarangKeluarActivity : BottomSheetDialogFragment() {

    private var barangkeluar: DataBarangKeluarItem? = null
    private var behavior: BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            barangkeluar = it.getParcelable(ARG_PARAM1)
        }
    }
//        setContentView(R.layout.activity_detail_barang)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val bottomSheets = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.activity_detail_barang_keluar, null)
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
        view.findViewById<TextView>(R.id.pengguna)?.text = barangkeluar?.pengguna
        view.findViewById<TextView>(R.id.kodeBarang)?.text = barangkeluar?.kodeBarang.toString()
        view.findViewById<TextView>(R.id.keterangan)?.text = barangkeluar?.keterangan
        view.findViewById<TextView>(R.id.tanggalkeluar)?.text = barangkeluar?.tanggalKeluar
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        (requireActivity() as ListBarangKeluarActivity).makeRequest()
    }

    companion object {
        @JvmStatic
        fun newInstance(barangkeluar: DataBarangKeluarItem?) =
            DetailBarangKeluarActivity().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, barangkeluar)
                }
            }
    }

}