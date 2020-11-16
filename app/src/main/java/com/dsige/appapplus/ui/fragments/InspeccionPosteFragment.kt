package com.dsige.appapplus.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
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
import com.dsige.appapplus.data.local.model.EstadoPoste
import com.dsige.appapplus.data.local.model.EstadoTrabajo
import com.dsige.appapplus.data.local.model.Filtro
import com.dsige.appapplus.data.local.model.InspeccionPoste
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.activities.RegistroInspeccionActivity
import com.dsige.appapplus.ui.adapters.EstadoAdapter
import com.dsige.appapplus.ui.adapters.EstadoPosteAdapter
import com.dsige.appapplus.ui.adapters.InspeccionPosteAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.google.gson.Gson
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.cardview_inspeccion_ot.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InspeccionPosteFragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        spinnerEstado()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    lateinit var f: Filtro
    private var tipo: Int = 0
    private var usuarioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        f = Filtro(54)
        arguments?.let {
            tipo = it.getInt(ARG_PARAM1)
            usuarioId = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inspeccion_poste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        val inspeccionAdapter =
            InspeccionPosteAdapter(object : OnItemClickListener.InspeccionListener {
                override fun onItemClick(i: InspeccionPoste, view: View, position: Int) {
                    when (view.id) {
                        R.id.imgMap -> {
                            if (i.latitud.isEmpty()) {
                                inspeccionViewModel.setError("No cuenta con coordenadas")
                                return
                            }
                        }
                        else -> startActivity(
                            Intent(context, RegistroInspeccionActivity::class.java)
                                .putExtra("id", i.inspeccionCampoId)
                                .putExtra("usuarioId", usuarioId)
                        )
                    }
                }
            })
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = inspeccionAdapter

        Handler().postDelayed({
            inspeccionViewModel.getInspecciones().observe(viewLifecycleOwner, {
                inspeccionAdapter.addItems(it)
            })
        }, 500)
        inspeccionViewModel.search.value = Gson().toJson(f)
        editTextEstado.setText("Asignado")
        editTextEstado.setOnClickListener(this)
    }

    private fun spinnerEstado() {
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

        textViewTitulo.text = String.format("Tipo de Estado")

        val estadoAdapter = EstadoPosteAdapter(object : OnItemClickListener.EstadoPosteListener {
            override fun onItemClick(e: EstadoPoste, view: View, position: Int) {
                editTextEstado.setText(e.descripcion)
                f.pageSize = e.estadoId
                val json = Gson().toJson(f)
                inspeccionViewModel.search.value = json
                dialog.dismiss()
            }
        })
        recyclerView.adapter = estadoAdapter
        inspeccionViewModel.getEstados().observe(this, {
            estadoAdapter.addItems(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            InspeccionPosteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}