package com.dsige.appapplus.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Grupo
import com.dsige.appapplus.data.local.model.OtHoja56
import com.dsige.appapplus.data.viewModel.HojaViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_hoja56.*
import javax.inject.Inject

class Hoja56Activity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> formHoja()
            R.id.editTextTipo -> dialogGroup()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var hojaViewModel: HojaViewModel
    lateinit var o: OtHoja56
    private var tipo: Int = 0
    private var formatoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoja56)

        o = OtHoja56()

        val b = intent.extras
        if (b != null) {
            bindUI(b.getString("title")!!, b.getInt("tipo"), b.getInt("formatoId"), b.getInt("id"))
        }
    }

    private fun bindUI(title: String, t: Int, f: Int, id: Int) {
        hojaViewModel =
            ViewModelProvider(this, viewModelFactory).get(HojaViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        tipo = t
        formatoId = f

        when (t) {
            5 -> textViewTitle.text = String.format("Tablero SP")
            6 -> textViewTitle.text = String.format("Tablero AP")
        }

        if (id != 0) {
            hojaViewModel.getHoja56ById(id).observe(this, Observer { q ->
                if (q != null) {
                    o.hoja56Id = q.hoja56Id
                    when (q.tipoTablero) {
                        1 -> chkAereo.isChecked = true
                        2 -> chkNivel.isChecked = true
                    }
                    editText1.setText(q.nroMedidor)
                    editTextTipo.setText(q.nombreTipoId)
                    editText2.setText(q.baseMovil)
                    editText3.setText(q.fusible)
                    editText4.setText(q.seccion)
                    editText5.setText(q.observacion)
                }
            })
        }

        fab.setOnClickListener(this)
        editTextTipo.setOnClickListener(this)

        hojaViewModel.success.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
                finish()
            }
        })

        hojaViewModel.error.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
            }
        })
    }

    private fun formHoja() {
        o.formatoId = formatoId
        o.item = tipo
        o.tipoTablero = when {
            chkAereo.isChecked -> 1
            chkNivel.isChecked -> 2
            else -> 0
        }
        o.nroMedidor = editText1.text.toString()
        o.baseMovil = editText2.text.toString()
        o.fusible = editText3.text.toString()
        o.seccion = editText4.text.toString()
        o.observacion = editText5.text.toString()
        hojaViewModel.validateHoja56(o)
    }

    private fun dialogGroup() {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AppTheme))
        @SuppressLint("InflateParams") val v =
            LayoutInflater.from(this).inflate(R.layout.dialog_combo, null)
        val textViewTitulo: TextView = v.findViewById(R.id.textViewTitulo)
        val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
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

        textViewTitulo.text = String.format("Tipo")

        val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
            override fun onItemClick(g: Grupo, view: View, position: Int) {
                o.idtipo = g.grupoId
                editTextTipo.setText(g.descripcion)
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        hojaViewModel.getGrupoById(8).observe(this, Observer { g ->
            if (g != null) {
                grupoAdapter.addItems(g)
            }
        })
    }
}