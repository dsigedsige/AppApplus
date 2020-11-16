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
import kotlinx.android.synthetic.main.fragment_poste_8.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"

class Poste8Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editText3 -> dialogGroup(3, "Conseción")
            R.id.fabSave -> formPoste8()
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
        return inflater.inflate(R.layout.fragment_poste_8, container, false)
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
                editText1.setText(it.infoAdd_RolloReserva)
                editText2.setText(it.infoAdd_Fotos)
                editText3.setText(it.id_infoAdd_ConsecionNombre)
                editText4.setText(it.infoAdd_Niveltension)
                editText6.setText(it.infoCnew_CableNuevo)
                editText7.setText(if (it.infoCnew_vanoIzq != 0.0) it.infoCnew_vanoIzq.toString() else null)
                editText8.setText(if (it.infoCnew_vanoDer != 0.0) it.infoCnew_vanoDer.toString() else null)
                editText9.setText(if (it.infoCnew_vanoAde != 0.0) it.infoCnew_vanoAde.toString() else null)
                editText10.setText(if (it.infoCnew_vanoAtra != 0.0) it.infoCnew_vanoAtra.toString() else null)
                editText11.setText(if (it.infoCnew_alturaInstala != 0.0) it.infoCnew_alturaInstala.toString() else null)
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
        editText3.setOnClickListener(this)
        fabSave.setOnClickListener(this)
    }

    private fun formPoste8() {

        p.infoAdd_RolloReserva = editText1.text.toString()
        p.infoAdd_Fotos = editText2.text.toString()
        p.infoAdd_Niveltension = editText4.text.toString()
        p.infoCnew_CableNuevo = editText6.text.toString()
        p.id_infoAdd_ConsecionNombre = editText3.text.toString()

        when {
            editText7.text.toString().isEmpty() -> p.infoCnew_vanoIzq = 0.0
            else -> p.infoCnew_vanoIzq = editText7.text.toString().toDouble()
        }
        when {
            editText8.text.toString().isEmpty() -> p.infoCnew_vanoDer = 0.0
            else -> p.infoCnew_vanoDer = editText8.text.toString().toDouble()
        }
        when {
            editText9.text.toString().isEmpty() -> p.infoCnew_vanoAde = 0.0
            else -> p.infoCnew_vanoAde = editText9.text.toString().toDouble()
        }
        when {
            editText10.text.toString().isEmpty() -> p.infoCnew_vanoAtra = 0.0
            else -> p.infoCnew_vanoAtra = editText10.text.toString().toDouble()
        }
        when {
            editText11.text.toString().isEmpty() -> p.infoCnew_alturaInstala = 0.0
            else -> p.infoCnew_alturaInstala = editText11.text.toString().toDouble()
        }


        inspeccionViewModel.validatePoste8(p)
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
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
        builder.setView(v)
        val dialog = builder.create()
        dialog.show()

        textViewTitulo.text = title

        val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
            override fun onItemClick(g: Grupo, view: View, position: Int) {
                when (tipo) {
                    3 -> {
                        p.id_infoAdd_Consecion = g.grupoId
                        editText3.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        inspeccionViewModel.getGrupoById(39).observe(this, { g ->
            if (g != null) {
                grupoAdapter.addItems(g)
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste8Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}