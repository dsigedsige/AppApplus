package com.dsige.appapplus.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Estado
import com.dsige.appapplus.data.local.model.Filtro
import com.dsige.appapplus.data.local.model.Ot
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.activities.FormatoActivity
import com.dsige.appapplus.ui.activities.ParteDiarioActivity
import com.dsige.appapplus.ui.adapters.EstadoAdapter
import com.dsige.appapplus.ui.adapters.OtAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : DaggerFragment(), View.OnClickListener, TextView.OnEditorActionListener {

    override fun onEditorAction(v: TextView, p1: Int, p2: KeyEvent?): Boolean {
        if (v.text.isNotEmpty()) {
            f.search = v.text.toString()
            val json = Gson().toJson(f)
            registroViewModel.search.value = json
        }
        return false
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editTextEstado -> spinnerEstado()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    private var tipo: Int = 0
    private var usuarioId: Int = 0
    lateinit var f: Filtro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        f = Filtro()

        arguments?.let {
            tipo = it.getInt(ARG_PARAM1)
            usuarioId = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)
        editTextEstado.setOnClickListener(this)
        val oTAdapter = OtAdapter(object : OnItemClickListener.OTListener {
            override fun onItemClick(o: Ot, view: View, position: Int) {
                when (o.estadoId) {
                    6 -> if (o.active == 2) {
                        Util.toastMensaje(context!!, "OT reasignado")
                    } else {
                        val popupMenu = PopupMenu(context!!, view)
                        popupMenu.menu.add(1, 1, 1, getText(R.string.acept))
                        popupMenu.menu.add(2, 2, 2, getText(R.string.notAcept))
                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                1 -> messageOT(o)
                                2 -> gOTActivity(6, o, false)
                            }
                            false
                        }
                        popupMenu.show()
                    }
                    8 -> {
                        val popupMenu = PopupMenu(context!!, view)
                        popupMenu.menu.add(1, 1, 1, getText(R.string.aperturar))
                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                1 -> messageParteDiario(o)
                            }
                            false
                        }
                        popupMenu.show()
                    }
                    else -> gOTActivity(o.estadoId, o, false)
                }
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = oTAdapter
        registroViewModel.getOtByTipoPaging()
            .observe(viewLifecycleOwner, Observer { s ->
                if (s != null) {
                    oTAdapter.addItems(s)
                }
            })

        f.pageSize = 6
        val json = Gson().toJson(f)
        registroViewModel.search.value = json

        editTextEstado.setText(String.format("Asignado al Proyectista"))
        editTextSearch.setOnEditorActionListener(this)


        registroViewModel.success.observe(viewLifecycleOwner, Observer {
            Util.toastMensaje(context!!, it)
        })
    }

    private fun gOTActivity(estado: Int, o: Ot, t: Boolean) {
        startActivity(
            Intent(
                context,
                if (estado == 9) ParteDiarioActivity::class.java else FormatoActivity::class.java
            )
                .putExtra("id", o.otId)
                .putExtra("usuarioId", usuarioId)
                .putExtra("estadoId", if (t) 0 else o.estadoId)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
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

        val estadoAdapter = EstadoAdapter(object : OnItemClickListener.EstadoListener {
            override fun onItemClick(e: Estado, view: View, position: Int) {
                editTextEstado.setText(e.descripcion)
                f.pageSize = e.estadoId
                f.search = ""
                editTextSearch.setText("")
                val json = Gson().toJson(f)
                registroViewModel.search.value = json
                dialog.dismiss()
            }
        })
        recyclerView.adapter = estadoAdapter

        registroViewModel.getEstados().observe(this, Observer { p ->
            if (p != null) {
                estadoAdapter.addItems(p)
            }
        })
    }

    private fun messageOT(ot: Ot) {
        val dialog = MaterialAlertDialogBuilder(context!!)
            .setTitle("Mensaje")
            .setMessage("Estas seguro de aceptar Ot ?")
            .setPositiveButton("SI") { dialog, _ ->
//                Util.toastMensajeShort(context!!, "Actualizando Estado...")
                registroViewModel.changeEstado(ot, 8)
                dialog.dismiss()
//                Handler().postDelayed({
//                    gOTActivity(6, ot, true)
//                    dialog.dismiss()
//                }, 600)
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun messageParteDiario(ot: Ot) {
        val dialog = MaterialAlertDialogBuilder(context!!)
            .setTitle("Mensaje")
            .setMessage("Deseas Aperturar Parte Diario ?")
            .setPositiveButton("SI") { dialog, _ ->
                registroViewModel.changeEstado(ot, 9)
                dialog.dismiss()
//                Handler().postDelayed({
//                    gOTActivity(6, ot, true)
//                    dialog.dismiss()
//                }, 600)
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }
}