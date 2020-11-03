package com.dsige.appapplus.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
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
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : DaggerFragment(), View.OnClickListener, TextView.OnEditorActionListener {

//    override fun onPrepareOptionsMenu(menu: Menu) {
//        super.onPrepareOptionsMenu(menu)
//        topMenu = menu
//    }
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.check -> {            }
//            R.id.send -> {            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

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
            R.id.fabEnvio -> messageOtMasive()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    lateinit var builder: AlertDialog.Builder
    var dialog: AlertDialog? = null

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
        setHasOptionsMenu(true)
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
                if (view is MaterialCheckBox) {
                    val checked: Boolean = view.isChecked
                    if (checked) {
                        registroViewModel.setOts(o)
                    } else {
                        registroViewModel.removeItemAt(o)
                    }
                    return
                }
                when (o.estadoId) {
                    6 -> if (o.active == 2) {
                        Util.toastMensaje(context!!, "OT reasignado")
                    } else {
                        val popupMenu = PopupMenu(context!!, view)
                        popupMenu.menu.add(1, 1, 1, getText(R.string.acept))
                        popupMenu.menu.add(2, 2, 2, getText(R.string.notAcept))
                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                1 -> messageDialog(o, 8, "Estas seguro de aceptar Ot ?")
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
                                1 -> messageDialog(o, 9, "Deseas Aperturar Parte Diario ?")
                            }
                            false
                        }
                        popupMenu.show()
                    }
                    9 -> {
                        val popupMenu = PopupMenu(context!!, view)
                        popupMenu.menu.add(1, 1, 1, getText(R.string.registrar))
                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                1 -> gOTActivity(o.estadoId, o, false)
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
            .observe(viewLifecycleOwner, { s ->
                if (s != null) {
                    oTAdapter.addItems(s)
                }
            })

        f.pageSize = 6
        val json = Gson().toJson(f)
        registroViewModel.search.value = json

        editTextEstado.setText(String.format("Asignado al Proyectista"))
        editTextSearch.setOnEditorActionListener(this)
        fabEnvio.setOnClickListener(this)

        registroViewModel.success.observe(viewLifecycleOwner, {
            registroViewModel.removeAll()
            closeLoad()

            if (it == "PD") {
                startActivity(
                    Intent(context, ParteDiarioActivity::class.java)
                        .putExtra("id", 0)
                        .putExtra("usuarioId", usuarioId)
                )
            } else
                Util.toastMensaje(context!!, it)
        })

        registroViewModel.otsData.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                fabEnvio.visibility = View.VISIBLE
                fabEnvio.text = String.format("Actualizar %s", it.size)
            } else {
                fabEnvio.visibility = View.GONE
            }
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
                registroViewModel.removeAll()
                fabEnvio.visibility = View.GONE
                dialog.dismiss()
            }
        })
        recyclerView.adapter = estadoAdapter
        registroViewModel.getEstados().observe(this, { p ->
            if (p != null) {
                estadoAdapter.addItems(p)
            }
        })
    }

    /**
     * ot = Ot
     * e = estado
     */
    private fun messageDialog(ot: Ot, e: Int, title: String) {
        val dialog = MaterialAlertDialogBuilder(context!!)
            .setTitle("Mensaje")
            .setMessage(title)
            .setPositiveButton("SI") { dialog, _ ->
                load()
                registroViewModel.sendEstadoOt(ot, e)
                dialog.dismiss()
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
                load()
                registroViewModel.sendEstadoOt(ot, 9)
                dialog.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun load() {
        builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
        @SuppressLint("InflateParams") val view =
            LayoutInflater.from(context).inflate(R.layout.dialog_login, null)
        builder.setView(view)
        val textViewTitle: TextView = view.findViewById(R.id.textView)
        textViewTitle.text = String.format("Actualizando..")
        dialog = builder.create()
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    private fun closeLoad() {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }
    }

    private fun messageOtMasive() {
        val dialog = MaterialAlertDialogBuilder(context!!)
            .setTitle("Mensaje")
            .setMessage("Deseas actualizar los campos seleccionados ?")
            .setPositiveButton("SI") { dialog, _ ->
                if (f.pageSize != 9) {
                    load()
                    when (f.pageSize) {
                        6 -> registroViewModel.changeMasiveEstado(8)
                        8 -> registroViewModel.changeMasiveEstado(9)
//                        9 -> registroViewModel.changeMasiveEstado(15)
                    }
                } else {
                    registroViewModel.addOtParteDiario()
                }
                dialog.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }
}