package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtDetalle
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_bmt_2.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class BMT2Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fabBefore -> pager.currentItem = 0
            R.id.fabSave -> formTwo()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    private var formatoId: Int = 0
    private var codigo: String = ""
    private var detalleId: Int = 0
    private lateinit var pager: ViewPager2
    lateinit var d: OtDetalle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        d = OtDetalle()

        arguments?.let {
            formatoId = it.getInt(ARG_PARAM1)
            codigo = it.getString(ARG_PARAM2)!!
            detalleId = it.getInt(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmt_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager = activity!!.findViewById(R.id.pager)
        bindUI()
    }

    private fun bindUI() {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)

        textViewCodigo.text = codigo

        registroViewModel.getFormById(detalleId).observe(viewLifecycleOwner, Observer { de ->
            if (de != null) {
                d = de
                editTextPastoralC.setText(de.pastotalC)
                editTextPastoralFG.setText(de.pastotalGF)
                editTextEquipoTipo.setText(de.equipoTipo)
                editTextEquipoModelo.setText(de.equipoModelo)
                editTextLamparaTipo.setText(de.lampara)
                editTextDireccion.setText(de.direccion)
            }
        })

        fabBefore.setOnClickListener(this)
        fabSave.setOnClickListener(this)

        registroViewModel.error.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
            }
        })

        registroViewModel.success.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                activity!!.finish()
            }
        })
    }

    private fun formTwo() {
        d.formatoDetalleId = detalleId
        d.pastotalC = editTextPastoralC.text.toString()
        d.pastotalGF = editTextPastoralFG.text.toString()
        d.equipoTipo = editTextEquipoTipo.text.toString()
        d.equipoModelo = editTextEquipoModelo.text.toString()
        d.lampara = editTextLamparaTipo.text.toString()
        d.direccion = editTextDireccion.text.toString()
        registroViewModel.validateFormTwo(d)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String, param3: Int) =
            BMT2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putInt(ARG_PARAM3, param3)
                }
            }
    }
}
