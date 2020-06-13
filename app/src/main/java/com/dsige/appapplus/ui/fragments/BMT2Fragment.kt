package com.dsige.appapplus.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Grupo
import com.dsige.appapplus.data.local.model.OtDetalle
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
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
            R.id.editTextPastoralC -> dialogGroup(10, "Pastoral C")
            R.id.editTextPastoralFG -> dialogGroup(11, "Pastoral FG")
            R.id.editTextLamparaTipo -> dialogGroup(12, "Tipo Lampara")
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
                editTextPastoralC.setText(de.nombrePastotalC)
                editTextPastoralFG.setText(de.nombrePastotalGF)
                editTextEquipoTipo.setText(de.equipoTipo)
                editTextEquipoModelo.setText(de.equipoModelo)
                editTextLamparaTipo.setText(de.nombreLampara)
                editTextDireccion.setText(de.direccion)
            }
        })

        fabBefore.setOnClickListener(this)
        fabSave.setOnClickListener(this)

        editTextPastoralC.setOnClickListener(this)
        editTextPastoralFG.setOnClickListener(this)
        editTextLamparaTipo.setOnClickListener(this)

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
        d.nombrePastotalC = editTextPastoralC.text.toString()
        d.nombrePastotalGF = editTextPastoralFG.text.toString()
        d.equipoTipo = editTextEquipoTipo.text.toString()
        d.equipoModelo = editTextEquipoModelo.text.toString()
        d.nombreLampara = editTextLamparaTipo.text.toString()
        d.direccion = editTextDireccion.text.toString()
        d.fecha = Util.getFecha()
        registroViewModel.validateFormBTTwo(d)
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

    private fun dialogGroup(id: Int, title: String) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
        @SuppressLint("InflateParams") val v =
            LayoutInflater.from(context).inflate(R.layout.dialog_combo, null)
        val textViewTitulo: TextView = v.findViewById(R.id.textViewTitulo)
        val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context, DividerItemDecoration.VERTICAL
            )
        )
        builder.setView(v)
        val dialog = builder.create()
        dialog.show()

        textViewTitulo.text = title

        val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
            override fun onItemClick(g: Grupo, view: View, position: Int) {
                when (id) {
                    10 -> {
                        d.pastotalC = g.grupoId.toString()
                        editTextPastoralC.setText(g.descripcion)
                    }
                    11 -> {
                        d.pastotalGF = g.grupoId.toString()
                        editTextPastoralFG.setText(g.descripcion)
                    }
                    12 -> {
                        d.lampara = g.grupoId.toString()
                        editTextLamparaTipo.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        registroViewModel.getGrupoById(id).observe(this, Observer { g ->
            if (g != null) {
                grupoAdapter.addItems(g)
            }
        })
    }
}