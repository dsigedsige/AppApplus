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
import kotlinx.android.synthetic.main.fragment_mt_1.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class MT1Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fabNext -> formOne()
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
        return inflater.inflate(R.layout.fragment_mt_1, container, false)
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
                editTextAlimentador.setText(de.alim)
                editTextAPC.setText(de.codigoSoporte)
                editTextVIA.setText(de.codigoVia)
                editTextLlave.setText(de.llave)
                editTextBT.setText(de.sistemas)
                editTextSoporte.setText(de.nombreTipoMaterialId)
                editTextTamaño.setText(de.tamanio)
                editTextSP.setText(de.redSDS)
                editTextAP.setText(de.redAP)
                editTextMT.setText(de.redAmbas)
                editTextCaja.setText(de.cajaDeriva)
                editTextV.setText(de.retenidaV)
                editTextS.setText(de.retenidaS)
            }
        })

        fabNext.setOnClickListener(this)
        editTextSoporte.setOnClickListener(this)
        editTextTamaño.setOnClickListener(this)

        registroViewModel.success.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
                pager.currentItem = 1
            }
        })

        registroViewModel.error.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
            }
        })
    }

    private fun formOne() {
        d.formatoDetalleId = detalleId
        d.formatoId = formatoId
        d.alim = editTextAlimentador.text.toString()
        d.codigoSoporte = editTextAPC.text.toString()
        d.codigoVia = editTextVIA.text.toString()
        d.llave = editTextLlave.text.toString()
        d.sistemas = editTextBT.text.toString()
        d.nombreTipoMaterialId = editTextSoporte.text.toString()
        d.tamanio = editTextTamaño.text.toString()
        d.redSDS = editTextSP.text.toString()
        d.redAP = editTextAP.text.toString()
        d.redAmbas = editTextMT.text.toString()
        d.cajaDeriva = editTextCaja.text.toString()
        d.retenidaV = editTextV.text.toString()
        d.retenidaS = editTextS.text.toString()

        registroViewModel.validateFormOne(d)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String, param3: Int) =
            MT1Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putInt(ARG_PARAM3, param3)
                }
            }
    }
}