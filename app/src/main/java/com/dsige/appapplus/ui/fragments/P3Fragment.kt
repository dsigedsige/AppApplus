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
import kotlinx.android.synthetic.main.fragment_p3.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class P3Fragment : DaggerFragment(), View.OnClickListener {

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
        return inflater.inflate(R.layout.fragment_p3, container, false)
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
                    editTextProyecto.setText(p.proyecto)
                    editTextTipoSistema.setText(p.tipoSistema)
                    editTextGrupoResistividad.setText(p.grupoResis)
                    editTextNivelResistividad.setText(p.nivelResis)

                    editTextBTNPozo.setText(p.btNpozos)
                    checkBoxBTPozoTratado.isChecked = p.btPozoTratado == "SI"
                    editTextBTNDosis.setText(p.btNdosis)
                    editTextBTCompuesto.setText(p.btCompuesto)

                    editTextMTNPozo.setText(p.mtNpozos)
                    checkBoxMTPozoTratado.isChecked = p.mtPozoTratado == "SI"
                    editTextMTNDosis.setText(p.mtNdosis)
                    editTextMTCompuesto.setText(p.mtCompuesto)
                    editTextObservacion.setText(p.observaciones)
                }
            })
        protocoloViewModel.success.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
                pager.currentItem = 3
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

        o.proyecto = editTextProyecto.text.toString()
        o.tipoSistema = editTextTipoSistema.text.toString()
        o.grupoResis = editTextGrupoResistividad.text.toString()
        o.nivelResis = editTextNivelResistividad.text.toString()

        o.btNpozos = editTextBTNPozo.text.toString()
        o.btPozoTratado = if(checkBoxBTPozoTratado.isChecked) "SI" else "NO"
        o.btNdosis = editTextBTNDosis.text.toString()
        o.btCompuesto = editTextBTCompuesto.text.toString()

        o.mtNpozos = editTextMTNPozo.text.toString()
        o.mtPozoTratado = if(checkBoxMTPozoTratado.isChecked) "SI" else "NO"
        o.mtNdosis = editTextMTNDosis.text.toString()
        o.mtCompuesto = editTextMTCompuesto.text.toString()

        o.observaciones = editTextObservacion.text.toString()
        protocoloViewModel.validateProtocoloP3(o)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            P3Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}
