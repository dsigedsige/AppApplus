package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtProtocolo
import com.dsige.appapplus.data.viewModel.ProtocoloViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_p1.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class P1Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        formProtocolo()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var protocoloViewModel: ProtocoloViewModel
    private lateinit var pager: ViewPager
    lateinit var o: OtProtocolo
    private var formatoId: Int = 0
    private var tipoProtocolo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o = OtProtocolo()

        arguments?.let {
            formatoId = it.getInt(ARG_PARAM1)
            tipoProtocolo = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_p1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager = activity!!.findViewById(R.id.viewPager)
        bindUI()
    }

    private fun bindUI() {
        protocoloViewModel =
            ViewModelProvider(this, viewModelFactory).get(ProtocoloViewModel::class.java)

        fabGenerate.setOnClickListener(this)

        protocoloViewModel.getProtocoloByTipo(formatoId, tipoProtocolo)
            .observe(viewLifecycleOwner, Observer { p ->
                if (p != null) {
                    o.protocoloId = p.protocoloId
                    editTextTipoTerreno.setText(p.tipoTerreno)
                    editTextEstadoTerreno.setText(p.estadoTerreno)
                    editTextResistenciaSeca.setText(p.resistenciaSeca)
                    editTextResistenciaHumeda.setText(p.resistenciaHumeda)
                    editTextFechaSeca.setText(p.fechaSeca)
                    editTextSecaA1.setText(p.aSeca1)
                    editTextSecaA2.setText(p.aSeca2)
                    editTextSecaA3.setText(p.aSeca3)
                    editTextFechaHumeda.setText(p.fechaHumeda)
                    editTextHumedaA1.setText(p.aHumeda1)
                    editTextHumedaA2.setText(p.aHumeda2)
                    editTextHumedaA3.setText(p.aHumeda3)
                }
            })

        protocoloViewModel.success.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
                pager.currentItem = 1
            }
        })

        protocoloViewModel.error.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
            }
        })
    }

    private fun formProtocolo() {
        o.formatoId = formatoId
        o.tipoProtocoloId = tipoProtocolo
        o.tipoTerreno = editTextTipoTerreno.text.toString()
        o.estadoTerreno = editTextEstadoTerreno.text.toString()
        o.resistenciaSeca = editTextResistenciaSeca.text.toString()
        o.resistenciaHumeda = editTextResistenciaHumeda.text.toString()
        o.fechaSeca = editTextFechaSeca.text.toString()
        o.aSeca1 = editTextSecaA1.text.toString()
        o.aSeca2 = editTextSecaA2.text.toString()
        o.aSeca3 = editTextSecaA3.text.toString()
        o.fechaHumeda = editTextFechaHumeda.text.toString()
        o.aHumeda1 = editTextHumedaA1.text.toString()
        o.aHumeda2 = editTextHumedaA2.text.toString()
        o.aHumeda3 = editTextHumedaA3.text.toString()
        protocoloViewModel.validateProtocolo(o)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            P1Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}