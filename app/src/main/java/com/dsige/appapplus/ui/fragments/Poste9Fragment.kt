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
import kotlinx.android.synthetic.main.fragment_poste_9.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

class Poste9Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editText1 -> dialogGroup(1, 40, "Cumplimiento DMS")
            R.id.editText2 -> dialogGroup(2, 41, "Poste Inclinado")
            R.id.editText3 -> dialogGroup(3, 41, "Poste con subida y bajada de BT")
            R.id.editText4 -> dialogGroup(4, 41, "Poste Saturado de Cables")
            R.id.editText7 -> dialogGroup(7, 42, "Factible/No Factible")
            R.id.editText8 -> dialogGroup(8, 43, "Observación Principal")
            R.id.fabSave -> formPoste9()
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
        return inflater.inflate(R.layout.fragment_poste_9, container, false)
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
                editText1.setText(it.obsC_CumplimientoNombre)
                editText2.setText(it.obsC_PosteInclinadoNombre)
                editText3.setText(it.obsC_PosteSubidaNombre)
                editText4.setText(it.obsC_PosteSaturadoNombre)
                editText5.setText(it.comentarios_obsC)
                editText7.setText(it.resFac_FactibleNombre)
                editText8.setText(it.id_resFac_ObsPrincipalNombre)
                editText9.setText(it.Obs_generales)
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
        editText2.setOnClickListener(this)
        editText3.setOnClickListener(this)
        editText4.setOnClickListener(this)
        editText7.setOnClickListener(this)
        editText8.setOnClickListener(this)
        fabSave.setOnClickListener(this)
    }

    private fun formPoste9() {

        p.obsC_CumplimientoNombre = editText1.text.toString()
        p.obsC_PosteInclinadoNombre = editText2.text.toString()
        p.obsC_PosteSubidaNombre = editText3.text.toString()
        p.obsC_PosteSaturadoNombre = editText4.text.toString()
        p.resFac_FactibleNombre = editText7.text.toString()
        p.id_resFac_ObsPrincipalNombre = editText8.text.toString()

        p.comentarios_obsC = editText5.text.toString()
        p.Obs_generales = editText9.text.toString()
        inspeccionViewModel.validatePoste9(p)
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
                        p.obsC_Cumplimiento = g.grupoId
                        editText1.setText(g.descripcion)
                    }
                    2 -> {
                        p.obsC_PosteInclinado = g.grupoId
                        editText2.setText(g.descripcion)
                    }
                    3 -> {
                        p.obsC_PosteSubida = g.grupoId
                        editText3.setText(g.descripcion)
                    }
                    4 -> {
                        p.obsC_PosteSaturado = g.grupoId
                        editText4.setText(g.descripcion)
                    }
                    7 -> {
                        p.resFac_Factible = g.grupoId
                        editText7.setText(g.descripcion)
                    }
                    8 -> {
                        p.id_resFac_ObsPrincipal = g.grupoId
                        editText8.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        inspeccionViewModel.getGrupoById(grupoId).observe(this, {
            grupoAdapter.addItems(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste9Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}