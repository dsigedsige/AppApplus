package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.InspeccionPoste
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_poste_3.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

class Poste3Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fabSave -> formPoste3()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0
    lateinit var p: InspeccionPoste

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = InspeccionPoste()
        arguments?.let {
            inspeccionId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poste_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        inspeccionViewModel.getInspeccionById(inspeccionId).observe(viewLifecycleOwner, {
            if (it != null) {
                p = it
                editText1.setText(if (it.vanoBT_AngIzq != 0.0) it.vanoBT_AngIzq.toString() else null)
                editText2.setText(if (it.vanoBT_AngDer != 0.0) it.vanoBT_AngDer.toString() else null)
                editText3.setText(if (it.vanoBT_AngAde != 0.0) it.vanoBT_AngAde.toString() else null)
                editText4.setText(if (it.vanoBT_AngAtra != 0.0) it.vanoBT_AngAtra.toString() else null)
                editText5.setText(if (it.vanoBT_VanIzq != 0.0) it.vanoBT_VanIzq.toString() else null)
                editText6.setText(if (it.vanoBT_VanDer != 0.0) it.vanoBT_VanDer.toString() else null)
                editText7.setText(if (it.vanoBT_VanAde != 0.0) it.vanoBT_VanAde.toString() else null)
                editText8.setText(if (it.vanoBT_VanAtra != 0.0) it.vanoBT_VanAtra.toString() else null)
                editText9.setText(if (it.AngRetenidaBt != 0.0) it.AngRetenidaBt.toString() else null)
                editText10.setText(it.comentariosVanoBt)
            }
        })
        inspeccionViewModel.success.observe(viewLifecycleOwner, {
            Util.toastMensaje(context!!, it)
            activity!!.finish()
        })
        inspeccionViewModel.error.observe(viewLifecycleOwner, {
            Util.toastMensaje(context!!, it)
            activity!!.finish()
        })
        fabSave.setOnClickListener(this)
    }

    private fun formPoste3() {
        when {
            editText1.text.toString().isEmpty() -> p.vanoBT_AngIzq = 0.0
            else -> p.vanoBT_AngIzq = editText1.text.toString().toDouble()
        }
        when {
            editText2.text.toString().isEmpty() -> p.vanoBT_AngDer = 0.0
            else -> p.vanoBT_AngDer = editText2.text.toString().toDouble()
        }
        when {
            editText3.text.toString().isEmpty() -> p.vanoBT_AngAde = 0.0
            else -> p.vanoBT_AngAde = editText3.text.toString().toDouble()
        }
        when {
            editText4.text.toString().isEmpty() -> p.vanoBT_AngAtra = 0.0
            else -> p.vanoBT_AngAtra = editText4.text.toString().toDouble()
        }
        when {
            editText5.text.toString().isEmpty() -> p.vanoBT_VanIzq = 0.0
            else -> p.vanoBT_VanIzq = editText5.text.toString().toDouble()
        }
        when {
            editText6.text.toString().isEmpty() -> p.vanoBT_VanDer = 0.0
            else -> p.vanoBT_VanDer = editText6.text.toString().toDouble()
        }
        when {
            editText7.text.toString().isEmpty() -> p.vanoBT_VanAde = 0.0
            else -> p.vanoBT_VanAde = editText7.text.toString().toDouble()
        }
        when {
            editText8.text.toString().isEmpty() -> p.vanoBT_VanAtra = 0.0
            else -> p.vanoBT_VanAtra = editText8.text.toString().toDouble()
        }
        when {
            editText9.text.toString().isEmpty() -> p.AngRetenidaBt = 0.0
            else -> p.AngRetenidaBt = editText8.text.toString().toDouble()
        }
        when {
            editText10.text.toString().isEmpty() -> p.vanoMT_VanAtra = 0.0
            else -> p.vanoMT_VanAtra = editText8.text.toString().toDouble()
        }
        p.comentariosVanoBt = editText10.text.toString()
        inspeccionViewModel.validatePoste3(p)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste3Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}