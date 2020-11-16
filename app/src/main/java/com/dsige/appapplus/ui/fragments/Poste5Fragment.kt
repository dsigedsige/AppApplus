package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.InspeccionCable
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_poste_5.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

class Poste5Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fabSave -> formPoste5()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0
    lateinit var p: InspeccionCable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = InspeccionCable()
        arguments?.let {
            inspeccionId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poste_5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        inspeccionViewModel.getInspeccionCableById(inspeccionId).observe(viewLifecycleOwner, {
            if (it != null) {
                p = it
                editText1.setText(it.tipoCable1)
                editText2.setText(if (it.condIzqCant1 != 0.0) it.condIzqCant1.toString() else null)
                editText3.setText(if (it.condIzqAltura1_Te != 0.0) it.condIzqAltura1_Te.toString() else null)
                editText4.setText(if (it.condIzqCant2 != 0.0) it.condIzqCant2.toString() else null)
                editText5.setText(if (it.condIzqAltura2_Te != 0.0) it.condIzqAltura2_Te.toString() else null)
                editText6.setText(it.tipoCable2)
                editText7.setText(if (it.condDerCant1 != 0.0) it.condDerCant1.toString() else null)
                editText8.setText(if (it.condDerAltura1_Te != 0.0) it.condDerAltura1_Te.toString() else null)
                editText9.setText(if (it.condDerCant2 != 0.0) it.condDerCant2.toString() else null)
                editText10.setText(if (it.condDerAltura2_Te != 0.0) it.condDerAltura2_Te.toString() else null)
                editText11.setText(it.cableAdss)
                editText12.setText(if (it.condAdeCant1 != 0.0) it.condAdeCant1.toString() else null)
                editText13.setText(if (it.condAdeAltura1_Te != 0.0) it.condAdeAltura1_Te.toString() else null)
                editText14.setText(if (it.condAdeCant2 != 0.0) it.condAdeCant2.toString() else null)
                editText15.setText(if (it.condAdeAltura2_Te != 0.0) it.condAdeAltura2_Te.toString() else null)
                editText16.setText(it.cableCoaxial)
                editText17.setText(if (it.condAtrasCant1 != 0.0) it.condAtrasCant1.toString() else null)
                editText18.setText(if (it.condAtrasAltura1_Te != 0.0) it.condAtrasAltura1_Te.toString() else null)
                editText19.setText(if (it.condAtrasCant2 != 0.0) it.condAtrasCant2.toString() else null)
                editText20.setText(if (it.condAtrasAltura2_Te != 0.0) it.condAtrasAltura2_Te.toString() else null)
                editText21.setText(it.otrosCables)
                editText22.setText(if (it.longCant1 != 0.0) it.longCant1.toString() else null)
                editText23.setText(if (it.longAltura1_Te != 0.0) it.longAltura1_Te.toString() else null)
                editText24.setText(if (it.longCant2 != 0.0) it.longCant2.toString() else null)
                editText25.setText(if (it.longAltura2_Te != 0.0) it.longAltura2_Te.toString() else null)
                editText26.setText(it.comentarioTele)
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

    private fun formPoste5() {
        when {
            editText2.text.toString().isEmpty() -> p.condIzqCant1 = 0.0
            else -> p.condIzqCant1 = editText2.text.toString().toDouble()
        }
        when {
            editText3.text.toString().isEmpty() -> p.condIzqAltura1_Te = 0.0
            else -> p.condIzqAltura1_Te = editText3.text.toString().toDouble()
        }
        when {
            editText4.text.toString().isEmpty() -> p.condIzqCant2 = 0.0
            else -> p.condIzqCant2 = editText4.text.toString().toDouble()
        }
        when {
            editText5.text.toString().isEmpty() -> p.condIzqAltura2_Te = 0.0
            else -> p.condIzqAltura2_Te = editText5.text.toString().toDouble()
        }
        when {
            editText7.text.toString().isEmpty() -> p.condDerCant1 = 0.0
            else -> p.condDerCant1 = editText7.text.toString().toDouble()
        }
        when {
            editText8.text.toString().isEmpty() -> p.condDerAltura1_Te = 0.0
            else -> p.condDerAltura1_Te = editText8.text.toString().toDouble()
        }
        when {
            editText9.text.toString().isEmpty() -> p.condDerCant2 = 0.0
            else -> p.condDerCant2 = editText9.text.toString().toDouble()
        }
        when {
            editText10.text.toString().isEmpty() -> p.condDerAltura2_Te = 0.0
            else -> p.condDerAltura2_Te = editText10.text.toString().toDouble()
        }
        when {
            editText12.text.toString().isEmpty() -> p.condAdeCant1 = 0.0
            else -> p.condAdeCant1 = editText12.text.toString().toDouble()
        }
        when {
            editText13.text.toString().isEmpty() -> p.condAdeAltura1_Te = 0.0
            else -> p.condAdeAltura1_Te = editText13.text.toString().toDouble()
        }
        when {
            editText14.text.toString().isEmpty() -> p.condAdeCant2 = 0.0
            else -> p.condAdeCant2 = editText14.text.toString().toDouble()
        }
        when {
            editText15.text.toString().isEmpty() -> p.condAdeAltura2_Te = 0.0
            else -> p.condAdeAltura2_Te = editText15.text.toString().toDouble()
        }
        when {
            editText17.text.toString().isEmpty() -> p.condAtrasCant1 = 0.0
            else -> p.condAtrasCant1 = editText17.text.toString().toDouble()
        }
        when {
            editText18.text.toString().isEmpty() -> p.condAtrasAltura1_Te = 0.0
            else -> p.condAtrasAltura1_Te = editText18.text.toString().toDouble()
        }
        when {
            editText19.text.toString().isEmpty() -> p.condAtrasCant2 = 0.0
            else -> p.condAtrasCant2 = editText19.text.toString().toDouble()
        }
        when {
            editText20.text.toString().isEmpty() -> p.condAtrasAltura2_Te = 0.0
            else -> p.condAtrasAltura2_Te = editText20.text.toString().toDouble()
        }
        when {
            editText22.text.toString().isEmpty() -> p.longCant1 = 0.0
            else -> p.longCant1 = editText22.text.toString().toDouble()
        }
        when {
            editText23.text.toString().isEmpty() -> p.longAltura1_Te = 0.0
            else -> p.longAltura1_Te = editText23.text.toString().toDouble()
        }
        when {
            editText24.text.toString().isEmpty() -> p.longCant2 = 0.0
            else -> p.longCant2 = editText24.text.toString().toDouble()
        }
        when {
            editText25.text.toString().isEmpty() -> p.longAltura2_Te = 0.0
            else -> p.longAltura2_Te = editText25.text.toString().toDouble()
        }
        p.inspeccionCampoId = inspeccionId
        p.tipoCable1 = editText1.text.toString()
        p.tipoCable2 = editText6.text.toString()
        p.cableAdss = editText11.text.toString()
        p.cableCoaxial = editText16.text.toString()
        p.otrosCables = editText21.text.toString()
        p.comentarioTele = editText26.text.toString()

        inspeccionViewModel.validatePoste5(p)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste5Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}