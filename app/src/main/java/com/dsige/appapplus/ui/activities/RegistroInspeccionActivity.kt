package com.dsige.appapplus.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import com.dsige.appapplus.helper.Gps
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_registro_inspeccion.*
import javax.inject.Inject

class RegistroInspeccionActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editText5 -> dialogGroup(5, 27, editText5.hint.toString())
            R.id.editText6 -> dialogGroup(6, 28, editText6.hint.toString())
            R.id.editText7 -> dialogGroup(7, 29, editText7.hint.toString())
            R.id.editText8 -> dialogGroup(8, 30, editText8.hint.toString())
            R.id.editText11 -> dialogGroup(11, 31, editText11.hint.toString())
            R.id.editText12 -> dialogGroup(12, 32, editText12.hint.toString())
            R.id.fabGenerate -> formGeneral()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0
    private var usuarioId: Int = 0
    lateinit var p: InspeccionPoste

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_inspecciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fab1 -> goPosteActivity(1, item.title.toString())
            R.id.fab2 -> goPosteActivity(2, item.title.toString())
            R.id.fab3 -> goPosteActivity(3, item.title.toString())
            R.id.fab4 -> goPosteActivity(4, item.title.toString())
            R.id.fab5 -> goPosteActivity(5, item.title.toString())
            R.id.fab6 -> goPosteActivity(6, item.title.toString())
            R.id.fab7 -> goPosteActivity(7, item.title.toString())
            R.id.fab8 -> goPosteActivity(8, item.title.toString())
            R.id.fab9 -> goPosteActivity(9, item.title.toString())
            R.id.fab10 -> startActivity(
                Intent(this, PhotoInspeccionesActivity::class.java)
                    .putExtra("id", inspeccionId)
                    .putExtra("usuarioId", usuarioId)
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_inspeccion)
        val b = intent.extras
        if (b != null) {
            bindUI(b.getInt("id"), b.getInt("usuarioId"))
        }
    }

    private fun bindUI(id: Int, u: Int) {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Datos Generales"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        inspeccionId = id
        usuarioId = u
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        inspeccionViewModel.getInspeccionById(id).observe(this, {
            if (it != null) {
                p = it
                editText1.setText(String.format("Item General :%s", it.itemGeneral))
                editText2.setText(it.utmX)
                editText3.setText(it.utmY)
                editText4.setText(it.nroPoste)
                editText5.setText(it.id_MaterialSoporteNombre)
                editText6.setText(it.id_TipoPosteNombre)
                editText7.setText(it.id_CargaTrabajoNombre)
                editText8.setText(it.id_TipoArmadoNombre)
                editText9.setText(if (it.alturaPoste != 0.0) it.alturaPoste.toString() else null)
                editText10.setText(if (it.alturaLibrePoste != 0.0) it.alturaLibrePoste.toString() else null)
                editText11.setText(it.id_estadoMensulaNombre)
                editText12.setText(it.id_NivelCriticidadNombre)
                editText13.setText(it.comentariosGenerales)
            }
        })

        inspeccionViewModel.success.observe(this, {
            Util.toastMensaje(this, it)
        })
        inspeccionViewModel.error.observe(this, {
            Util.toastMensaje(this, it)
        })
        editText5.setOnClickListener(this)
        editText6.setOnClickListener(this)
        editText7.setOnClickListener(this)
        editText8.setOnClickListener(this)
        editText11.setOnClickListener(this)
        editText12.setOnClickListener(this)
        fabGenerate.setOnClickListener(this)
    }

    private fun formGeneral() {
        val gps = Gps(this)
        if (gps.isLocationEnabled()) {
            if (gps.latitude.toString() != "0.0" || gps.longitude.toString() != "0.0") {
                p.utmX = editText2.text.toString()
                p.utmY = editText3.text.toString()
                p.nroPoste = editText4.text.toString()
                p.id_MaterialSoporteNombre = editText5.text.toString()
                p.id_TipoPosteNombre = editText6.text.toString()
                p.id_CargaTrabajoNombre = editText7.text.toString()
                p.id_TipoArmadoNombre = editText8.text.toString()
                p.id_estadoMensulaNombre = editText11.text.toString()
                p.id_NivelCriticidadNombre = editText12.text.toString()
                p.usuarioId = usuarioId
                p.latitud = gps.latitude.toString()
                p.longitud = gps.longitude.toString()
                p.active = 1

                when {
                    editText9.text.toString().isEmpty() -> p.alturaPoste = 0.0
                    else -> p.alturaPoste = editText9.text.toString().toDouble()
                }
                when {
                    editText10.text.toString().isEmpty() -> p.alturaLibrePoste = 0.0
                    else -> p.alturaLibrePoste = editText10.text.toString().toDouble()
                }
                p.comentariosGenerales = editText13.text.toString()
                inspeccionViewModel.validateFormGeneral(p)
            }
        } else {
            gps.showSettingsAlert(this)
        }
    }

    private fun goPosteActivity(tipo: Int, title: String) {
        startActivity(
            Intent(this, FormPosteActivity::class.java)
                .putExtra("tipo", tipo)
                .putExtra("id", inspeccionId)
                .putExtra("title", title)
        )
    }

    private fun dialogGroup(tipo: Int, groupId: Int, title: String) {
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

        textViewTitulo.text = title

        val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
            override fun onItemClick(g: Grupo, view: View, position: Int) {
                when (tipo) {
                    5 -> {
                        p.id_MaterialSoporte = g.grupoId
                        editText5.setText(g.descripcion)
                    }
                    6 -> {
                        p.id_TipoPoste = g.grupoId
                        editText6.setText(g.descripcion)
                    }
                    7 -> {
                        p.id_CargaTrabajo = g.grupoId
                        editText7.setText(g.descripcion)
                    }
                    8 -> {
                        p.id_TipoArmado = g.grupoId
                        editText8.setText(g.descripcion)
                    }
                    11 -> {
                        p.id_estadoMensula = g.grupoId
                        editText11.setText(g.descripcion)
                    }
                    12 -> {
                        p.id_NivelCriticidad = g.grupoId
                        editText12.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        inspeccionViewModel.getGrupoById(groupId).observe(this, {
            grupoAdapter.addItems(it)
        })
    }
}