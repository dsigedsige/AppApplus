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
import com.dsige.appapplus.data.local.model.InspeccionPoste
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_poste_6.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

class Poste6Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editText1 -> dialogGroup(1, 38, editText1.hint.toString())
            R.id.editText4 -> dialogGroup(4, 36, editText4.hint.toString())
            R.id.editText5 -> dialogGroup(5, 38, editText5.hint.toString())
            R.id.editText8 -> dialogGroup(8, 36, editText8.hint.toString())
            R.id.fabSave -> formPoste6()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0
    lateinit var p: InspeccionPoste

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = InspeccionPoste()
        arguments?.let {
            inspeccionId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poste_6, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        inspeccionViewModel.getInspeccionById(inspeccionId).observe(viewLifecycleOwner, {
            if (it != null) {
                p = it
                editText1.setText(it.id_reteBT_tipoR1Nombre)
                editText2.setText(if (it.reteBT_AlturaR1 != 0.0) it.reteBT_AlturaR1.toString() else null)
                editText3.setText(it.reteBT_DirR1)
                editText4.setText(it.id_reteBT_Estado1Nombre)
                editText5.setText(it.id_reteBT_tipoR2Nombre)
                editText6.setText(if (it.reteBT_AlturaR1 != 0.0) it.reteBT_AlturaR2.toString() else null)
                editText7.setText(it.reteBT_DirR2)
                editText8.setText(it.id_reteBT_Estado2Nombre)
                editText9.setText(it.comentarios_ReteBT)
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
        editText1.setOnClickListener(this)
        editText4.setOnClickListener(this)
        editText5.setOnClickListener(this)
        editText8.setOnClickListener(this)
        fabSave.setOnClickListener(this)
    }

    private fun formPoste6() {
        when {
            editText2.text.toString().isEmpty() -> p.reteBT_AlturaR1 = 0.0
            else -> p.reteBT_AlturaR1 = editText2.text.toString().toDouble()
        }
        when {
            editText6.text.toString().isEmpty() -> p.reteBT_AlturaR2 = 0.0
            else -> p.reteBT_AlturaR2 = editText6.text.toString().toDouble()
        }

        p.id_reteBT_tipoR1Nombre = editText1.text.toString()
        p.reteBT_DirR1 = editText3.text.toString()
        p.id_reteBT_Estado1Nombre = editText4.text.toString()
        p.id_reteBT_tipoR2Nombre = editText5.text.toString()
        p.reteBT_DirR2 = editText7.text.toString()
        p.id_reteBT_Estado2Nombre = editText8.text.toString()
        p.comentarios_ReteBT = editText9.text.toString()
        inspeccionViewModel.validatePoste6(p)
    }

    private fun dialogGroup(tipo: Int, grupoId: Int, title: String) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
        @SuppressLint("InflateParams") val v =
            LayoutInflater.from(context).inflate(R.layout.dialog_combo, null)
        val textViewTitulo: TextView = v.findViewById(R.id.textViewTitulo)
        val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
        builder.setView(v)
        val dialog = builder.create()
        dialog.show()

        textViewTitulo.text = title

        val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
            override fun onItemClick(g: Grupo, view: View, position: Int) {
                when (tipo) {
                    1 -> {
                        p.id_reteBT_tipoR1 = g.grupoId
                        editText1.setText(g.descripcion)
                    }
                    4 -> {
                        p.id_reteBT_Estado1 = g.grupoId
                        editText4.setText(g.descripcion)
                    }
                    5 -> {
                        p.id_reteBT_tipoR2 = g.grupoId
                        editText5.setText(g.descripcion)
                    }
                    8 -> {
                        p.id_reteBT_Estado2 = g.grupoId
                        editText8.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        inspeccionViewModel.getGrupoById(grupoId).observe(this, { g ->
            if (g != null) {
                grupoAdapter.addItems(g)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste6Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}