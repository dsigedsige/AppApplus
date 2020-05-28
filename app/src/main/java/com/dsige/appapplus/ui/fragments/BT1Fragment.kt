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
import kotlinx.android.synthetic.main.fragment_bt_1.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class BT1Fragment : DaggerFragment(), View.OnClickListener {

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
        return inflater.inflate(R.layout.fragment_bt_1, container, false)
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
                editTextCodigoSoporte.setText(de.codigoSoporte)
                editTextAlim.setText(de.alim)
                editTextArmado.setText(de.armado)
                editTextMaterial.setText(de.nombreTipoMaterialId)
                editTextTamaño.setText(de.tamanio)
                editTextFuncion.setText(de.funcionId)

                checkSDS.isChecked = de.redSDS == "SI"
                checkAP.isChecked = de.redAP == "SI"
                checkAmbas.isChecked = de.redAmbas == "SI"

                editTextNro.setText(de.cNumeroId)
                editTextCod.setText(de.seccCod)
                editTextCap.setText(de.seccCap)
                editTextFus.setText(de.seccFus)
                editTextTipo.setText(de.tipoConductorId)
                editTextLVano.setText(de.lvano)
                editTextSeccion.setText(de.conduSecc)
                editTextFase.setText(de.conduFases)
            }
        })

        fabNext.setOnClickListener(this)
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
        d.codigoSoporte = editTextCodigoSoporte.text.toString()
        d.alim = editTextAlim.text.toString()
        d.armado = editTextArmado.text.toString()
        d.nombreTipoMaterialId = editTextMaterial.text.toString()
        d.tamanio = editTextTamaño.text.toString()
        d.funcionId = editTextFuncion.text.toString()
        d.redSDS = if (checkSDS.isChecked) "SI" else "NO"
        d.redAP = if (checkAP.isChecked) "SI" else "NO"
        d.redAmbas = if (checkAmbas.isChecked) "SI" else "NO"
        d.cNumeroId = editTextNro.text.toString()
        d.seccCod = editTextCod.text.toString()
        d.seccCap = editTextCap.text.toString()
        d.seccFus = editTextFus.text.toString()
        d.tipoConductorId = editTextTipo.text.toString()
        d.lvano = editTextLVano.text.toString()
        d.conduSecc = editTextSeccion.text.toString()
        d.conduFases = editTextFase.text.toString()
        registroViewModel.validateFormOne(d)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String, param3: Int) =
            BT1Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putInt(ARG_PARAM3, param3)
                }
            }
    }
}
