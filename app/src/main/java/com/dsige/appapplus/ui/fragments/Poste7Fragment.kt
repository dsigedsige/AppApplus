package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.InspeccionEquipo
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_poste_7.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

class Poste7Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fabSave -> formPoste7()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0
    lateinit var p: InspeccionEquipo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = InspeccionEquipo()
        arguments?.let {
            inspeccionId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poste_7, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        inspeccionViewModel.getInspeccionEquipoById(inspeccionId).observe(viewLifecycleOwner, {
            if (it != null) {
                p = it
                editText1.setText(it.electrico1)
                editText2.setText(if (it.cantidad1 != 0.0) it.cantidad1.toString() else null)
                editText3.setText(it.telecomunica1)
                editText4.setText(if (it.telecantidad1 != 0.0) it.telecantidad1.toString() else null)
                editText5.setText(it.electrico2)
                editText6.setText(if (it.cantidad2 != 0.0) it.cantidad2.toString() else null)
                editText7.setText(it.telecomunica2)
                editText8.setText(if (it.telecantidad2 != 0.0) it.telecantidad2.toString() else null)
                editText9.setText(it.electrico3)
                editText10.setText(if (it.cantidad3 != 0.0) it.cantidad3.toString() else null)
                editText11.setText(it.telecomunica3)
                editText12.setText(if (it.telecantidad3 != 0.0) it.telecantidad3.toString() else null)
                editText13.setText(it.electrico4)
                editText14.setText(if (it.cantidad4 != 0.0) it.cantidad4.toString() else null)
                editText15.setText(it.telecomunica4)
                editText16.setText(if (it.telecantidad4 != 0.0) it.telecantidad4.toString() else null)
                editText17.setText(it.comentario)
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

    private fun formPoste7() {
        p.inspeccionCampoId = inspeccionId
        p.electrico1 = editText1.text.toString()
        p.telecomunica1 = editText3.text.toString()
        p.electrico2 = editText5.text.toString()
        p.telecomunica2 = editText7.text.toString()
        p.electrico3 = editText9.text.toString()
        p.telecomunica3 = editText11.text.toString()
        p.electrico4 = editText13.text.toString()
        p.telecomunica4 = editText15.text.toString()
        p.comentario = editText17.text.toString()

        when {
            editText2.text.toString().isEmpty() -> p.cantidad1 = 0.0
            else -> p.cantidad1 = editText2.text.toString().toDouble()
        }
        when {
            editText4.text.toString().isEmpty() -> p.telecantidad1 = 0.0
            else -> p.telecantidad1 = editText4.text.toString().toDouble()
        }
        when {
            editText6.text.toString().isEmpty() -> p.cantidad2 = 0.0
            else -> p.cantidad2 = editText6.text.toString().toDouble()
        }
        when {
            editText8.text.toString().isEmpty() -> p.telecantidad2 = 0.0
            else -> p.telecantidad2 = editText8.text.toString().toDouble()
        }
        when {
            editText10.text.toString().isEmpty() -> p.cantidad3 = 0.0
            else -> p.cantidad3 = editText10.text.toString().toDouble()
        }
        when {
            editText12.text.toString().isEmpty() -> p.telecantidad3 = 0.0
            else -> p.telecantidad3 = editText12.text.toString().toDouble()
        }
        when {
            editText14.text.toString().isEmpty() -> p.cantidad4 = 0.0
            else -> p.cantidad4 = editText14.text.toString().toDouble()
        }
        when {
            editText16.text.toString().isEmpty() -> p.telecantidad4 = 0.0
            else -> p.telecantidad4 = editText16.text.toString().toDouble()
        }
        inspeccionViewModel.validatePoste7(p)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste7Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}