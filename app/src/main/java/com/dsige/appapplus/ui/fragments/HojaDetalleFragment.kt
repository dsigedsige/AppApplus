package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtHoja123
import com.dsige.appapplus.data.local.model.OtHoja56
import com.dsige.appapplus.data.viewModel.HojaViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hoja_detalle.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HojaDetalleFragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        formHoja()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var hojaViewModel: HojaViewModel

    private var formatoId: Int = 0
    private var item: Int = 0

    lateinit var oHoja123: OtHoja123
    lateinit var oHoja567: OtHoja56

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        oHoja123 = OtHoja123()
        oHoja567 = OtHoja56()
        arguments?.let {
            formatoId = it.getInt(ARG_PARAM1)
            item = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hoja_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        hojaViewModel =
            ViewModelProvider(this, viewModelFactory).get(HojaViewModel::class.java)

        when (item) {
            7 -> {
                layout1.hint = "Nro Medidor"
                layout2.hint = "Foto Célula"
                layout3.hint = "Contactor"
                layout4.hint = "Int. Horario"
                layout5.visibility = View.VISIBLE
            }
        }

        when (item) {
            3 -> otHoja123()
            7 -> otHoja567()
        }

        fab.setOnClickListener(this)
    }

    private fun otHoja123() {
        hojaViewModel.getHoja123ByItem(item, formatoId).observe(viewLifecycleOwner, Observer { q ->
            if (q != null) {
                oHoja123 = q
                editText1.setText(q.equipo)
                editText2.setText(q.kardex)
                editText3.setText(q.marca)
                editText4.setText(q.tipo)
            }
        })
    }

    private fun otHoja567() {
        hojaViewModel.getHoja567ByItem(item, formatoId).observe(viewLifecycleOwner, Observer { q ->
            if (q != null) {
                oHoja567 = q
                editText1.setText(q.nroMedidor)
                editText2.setText(q.fotocelula)
                editText3.setText(q.contactor)
                editText4.setText(q.intHorario)
                editText5.setText(q.observacion)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            HojaDetalleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

    private fun formHoja() {
        when (item) {
            3 -> {
                oHoja123.formatoId = formatoId
                oHoja123.item = item
                oHoja123.equipo = editText1.text.toString()
                oHoja123.kardex = editText2.text.toString()
                oHoja123.marca = editText3.text.toString()
                oHoja123.tipo = editText4.text.toString()
                oHoja123.fecha = Util.getFecha()
                hojaViewModel.validateHoja3(oHoja123)
            }
            7 -> {
                oHoja567.formatoId = formatoId
                oHoja567.item = item
                oHoja567.nroMedidor = editText1.text.toString()
                oHoja567.fotocelula = editText2.text.toString()
                oHoja567.contactor = editText3.text.toString()
                oHoja567.intHorario = editText4.text.toString()
                oHoja567.observacion = editText5.text.toString()
                oHoja567.fecha = Util.getFecha()
                hojaViewModel.validateHoja7(oHoja567)
            }
        }
    }
}