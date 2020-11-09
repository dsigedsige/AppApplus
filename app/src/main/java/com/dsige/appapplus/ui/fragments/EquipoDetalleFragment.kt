package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtEquipo
import com.dsige.appapplus.data.viewModel.EquipoViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_equipo_detalle.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EquipoDetalleFragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        formEquipo()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var equipoViewModel: EquipoViewModel
    private var tipo: Int = 0
    private var formatoId: Int = 0
    lateinit var e: OtEquipo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        e = OtEquipo()
        arguments?.let {
            tipo = it.getInt(ARG_PARAM1)
            formatoId = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_equipo_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        equipoViewModel =
            ViewModelProvider(this, viewModelFactory).get(EquipoViewModel::class.java)

        equipoViewModel.getEquipoDetalle(tipo,formatoId)
            .observe(viewLifecycleOwner, Observer { q ->
                if (q != null) {
                    e = q
                    editText1.setText(q.equipo)
                    editText2.setText(q.nroKardex)
                    editText3.setText(q.marca)
                    editText4.setText(q.tipo)
                }
            })

        fab.setOnClickListener(this)

        equipoViewModel.success.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
            }
        })

        equipoViewModel.error.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            EquipoDetalleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

    private fun formEquipo() {
        e.formatoId = formatoId
        e.tipoEquipo = tipo
        e.equipo = editText1.text.toString()
        e.nroKardex = editText2.text.toString()
        e.marca = editText3.text.toString()
        e.tipo = editText4.text.toString()
        e.fecha = Util.getFecha()
        equipoViewModel.validateEquipo(e)
    }
}