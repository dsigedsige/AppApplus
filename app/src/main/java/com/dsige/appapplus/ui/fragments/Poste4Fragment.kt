package com.dsige.appapplus.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Grupo
import com.dsige.appapplus.data.local.model.InspeccionCable
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_poste_4.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Poste4Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editText2 -> dialogGroup(2, "Cond Izq (C1) - Tipo")
            R.id.editText4 -> dialogGroup(4, "Cond Izq (C2) - Tipo")
            R.id.editText7 -> dialogGroup(7, "Cond Der (C1) - Tipo")
            R.id.editText9 -> dialogGroup(9, "Cond Der (C2) - Tipo")
            R.id.editText12 -> dialogGroup(12, "Cond Adel (C1) - Tipo")
            R.id.editText14 -> dialogGroup(14, "Cond ADel (C2) - Tipo")
            R.id.editText16 -> dialogGroup(16, "Cond Atras (C1) - Tipo")
            R.id.editText18 -> dialogGroup(18, "Cond Atras (C2) - Tipo")
            R.id.fabSave -> formPoste4()
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
        return inflater.inflate(R.layout.fragment_poste_4, container, false)
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
                editText1.setText(if (it.circuitoBT != 0.0) it.circuitoBT.toString() else null)
                editText2.setText(it.condIzqTipo1Nombre)
                editText3.setText(if (it.condIzqAltura1_BT != 0.0) it.condIzqAltura1_BT.toString() else null)
                editText4.setText(it.condIzqTipo2Nombre)
                editText5.setText(if (it.condIzqAltura2_BT != 0.0) it.condIzqAltura2_BT.toString() else null)
                editText6.setText(if (it.cableBT != 0.0) it.cableBT.toString() else null)
                editText7.setText(it.condDerTipo1Nombre)
                editText8.setText(if (it.condDerAltura1_BT != 0.0) it.condDerAltura1_BT.toString() else null)
                editText9.setText(it.condDerTipo2Nombre)
                editText10.setText(if (it.condDerAltura2_BT != 0.0) it.condDerAltura2_BT.toString() else null)
                editText11.setText(if (it.longbrazo != 0.0) it.longbrazo.toString() else null)
                editText12.setText(it.condAdeTipo1Nombre)
                editText13.setText(if (it.condAdeAltura1_BT != 0.0) it.condAdeAltura1_BT.toString() else null)
                editText14.setText(it.condAdeTipo2Nombre)
                editText15.setText(if (it.condAdeAltura2_BT != 0.0) it.condAdeAltura2_BT.toString() else null)
                editText16.setText(it.condAtrasTipo1Nombre)
                editText17.setText(if (it.condAtrasAltura1_BT != 0.0) it.condAtrasAltura1_BT.toString() else null)
                editText18.setText(it.condAtrasTipo2Nombre)
                editText19.setText(if (it.condAtrasAltura2_BT != 0.0) it.condAtrasAltura2_BT.toString() else null)
                editText20.setText(it.comentarioCableBT)
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
        editText2.setOnClickListener(this)
        editText4.setOnClickListener(this)
        editText7.setOnClickListener(this)
        editText9.setOnClickListener(this)
        editText12.setOnClickListener(this)
        editText14.setOnClickListener(this)
        editText16.setOnClickListener(this)
        editText18.setOnClickListener(this)
        fabSave.setOnClickListener(this)
    }

    private fun formPoste4() {
        p.inspeccionCampoId = inspeccionId
        p.condIzqTipo1Nombre = editText2.text.toString()
        p.condIzqTipo2Nombre = editText4.text.toString()
        p.condDerTipo1Nombre = editText7.text.toString()
        p.condDerTipo2Nombre = editText9.text.toString()
        p.condAdeTipo1Nombre = editText12.text.toString()
        p.condAdeTipo2Nombre = editText14.text.toString()
        p.condAtrasTipo1Nombre = editText16.text.toString()
        p.condAtrasTipo2Nombre = editText18.text.toString()

        when {
            editText1.text.toString().isEmpty() -> p.circuitoBT = 0.0
            else -> p.circuitoBT = editText1.text.toString().toDouble()
        }
        when {
            editText3.text.toString().isEmpty() -> p.condIzqAltura1_BT = 0.0
            else -> p.condIzqAltura1_BT = editText3.text.toString().toDouble()
        }
        when {
            editText5.text.toString().isEmpty() -> p.condIzqAltura2_BT = 0.0
            else -> p.condIzqAltura2_BT = editText5.text.toString().toDouble()
        }
        when {
            editText1.text.toString().isEmpty() -> p.cableBT = 0.0
            else -> p.cableBT = editText1.text.toString().toDouble()
        }
        when {
            editText8.text.toString().isEmpty() -> p.circuitoBT = 0.0
            else -> p.circuitoBT = editText8.text.toString().toDouble()
        }
        when {
            editText10.text.toString().isEmpty() -> p.condDerAltura2_BT = 0.0
            else -> p.condDerAltura2_BT = editText10.text.toString().toDouble()
        }
        when {
            editText11.text.toString().isEmpty() -> p.longbrazo = 0.0
            else -> p.longbrazo = editText11.text.toString().toDouble()
        }
        when {
            editText13.text.toString().isEmpty() -> p.condAdeAltura1_BT = 0.0
            else -> p.condAdeAltura1_BT = editText13.text.toString().toDouble()
        }
        when {
            editText15.text.toString().isEmpty() -> p.condAdeAltura2_BT = 0.0
            else -> p.condAdeAltura2_BT = editText1.text.toString().toDouble()
        }
        when {
            editText17.text.toString().isEmpty() -> p.condAtrasAltura1_BT = 0.0
            else -> p.condAtrasAltura1_BT = editText17.text.toString().toDouble()
        }
        when {
            editText19.text.toString().isEmpty() -> p.condAtrasAltura2_BT = 0.0
            else -> p.condAtrasAltura2_BT = editText19.text.toString().toDouble()
        }
        p.comentarioCableBT = editText20.text.toString()
        inspeccionViewModel.validatePoste4(p)
    }

    private fun dialogGroup(tipo: Int, title: String) {
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
                when (tipo) {
                    2 -> {
                        p.condIzqTipo1 = g.grupoId
                        editText2.setText(g.descripcion)
                    }
                    4 -> {
                        p.condIzqTipo2 = g.grupoId
                        editText4.setText(g.descripcion)
                    }
                    7 -> {
                        p.condDerTipo1 = g.grupoId
                        editText7.setText(g.descripcion)
                    }
                    9 -> {
                        p.condDerTipo2 = g.grupoId
                        editText9.setText(g.descripcion)
                    }
                    12 -> {
                        p.condAdeTipo1 = g.grupoId
                        editText12.setText(g.descripcion)
                    }
                    14 -> {
                        p.condAdeTipo2 = g.grupoId
                        editText14.setText(g.descripcion)
                    }
                    16 -> {
                        p.condAtrasTipo1 = g.grupoId
                        editText16.setText(g.descripcion)
                    }
                    18 -> {
                        p.condAtrasTipo2 = g.grupoId
                        editText18.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        inspeccionViewModel.getGrupoById(37).observe(this, {
            grupoAdapter.addItems(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste4Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}