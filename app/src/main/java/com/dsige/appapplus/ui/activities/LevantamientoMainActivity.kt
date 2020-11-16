package com.dsige.appapplus.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
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
import com.dsige.appapplus.data.local.model.Cadista
import com.dsige.appapplus.data.local.model.OtCabecera
import com.dsige.appapplus.data.local.model.Supervisor
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Gps
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.CadistaAdapter
import com.dsige.appapplus.ui.adapters.SupervisorAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_levantamiento_main.*
import java.util.*
import javax.inject.Inject

class LevantamientoMainActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editTextCombo -> spinnerCombo(1, "Cadista")
            R.id.editTextSupervisor -> spinnerCombo(2, "Supervisores")
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    lateinit var o: OtCabecera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levantamiento_main)

        o = OtCabecera()

        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getString("title")!!, b.getInt("tipo"), b.getInt("id"), b.getString("codigo")!!,
                b.getInt("otId"), b.getInt("estado"), b.getInt("usuarioId")
            )
        }
    }

    private fun bindUI(
        title: String, tipo: Int, id: Int, codigo: String, otId: Int, estado: Int, usuarioId: Int
    ) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        textViewCodigo.text = codigo

        registroViewModel.getHojaById(id).observe(this, { ot ->
            if (ot != null) {
                o = ot
                editTextNroSed.setText(ot.sed)
                editTextAlimentador.setText(ot.alimentador)
                chkDibujo.isChecked = ot.dibujar == "1"
                editTextCombo.setText(ot.nombreCadista)
                editTextSupervisor.setText(ot.nombreSupervisor)
            }
        })

        fab.setOnClickListener {
            val gps = Gps(this)
            if (gps.isLocationEnabled()) {
                if (gps.latitude.toString() != "0.0" || gps.longitude.toString() != "0.0") {
                    o.coordenadaX = gps.latitude.toString()
                    o.coordenadaY = gps.longitude.toString()

                    o.formatoId = id
                    o.tipoFormatoId = tipo
                    o.nombreTipoFormato = title
                    o.nroOt = codigo
                    o.otId = otId
                    o.active = 1
                    o.sed = editTextNroSed.text.toString().toUpperCase(Locale.getDefault())
                    o.alimentador = editTextAlimentador.text.toString()
                    o.dibujar = if (chkDibujo.isChecked) "1" else "0"
                    o.usuario = usuarioId
                    o.fechaRegistro = Util.getFecha()
                    o.nombreCadista = editTextCombo.text.toString()
                    o.nombreSupervisor = editTextSupervisor.text.toString()
                    registroViewModel.validateHoja(o)
                    Util.toastMensaje(this, "Verificando...")
                }
            } else {
                gps.showSettingsAlert(this)
            }
        }

        registroViewModel.success.observe(this, { s ->
            if (s != null) {
                startActivity(
                    Intent(this@LevantamientoMainActivity, PhotoActivity::class.java)
                        .putExtra("id", id)
                        .putExtra("otId", otId)
                        .putExtra("title", title)
                        .putExtra("tipo", tipo)
                        .putExtra("codigo", codigo)
                        .putExtra("estado", 1)
                        .putExtra("usuarioId", usuarioId)
                )
                finish()
            }
        })

        registroViewModel.error.observe(this, { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
            }
        })

        editTextCombo.setOnClickListener(this)
        editTextSupervisor.setOnClickListener(this)
    }

    private fun spinnerCombo(tipo: Int, title: String) {
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
        when (tipo) {
            1 -> {
                val cadistaAdapter = CadistaAdapter(object : OnItemClickListener.CadistaListener {
                    override fun onItemClick(c: Cadista, view: View, position: Int) {
                        o.cadistaId = c.cadistaId
                        editTextCombo.setText(c.nombre)
                        dialog.dismiss()
                    }
                })
                recyclerView.adapter = cadistaAdapter
                registroViewModel.getCadistas().observe(this, { g ->
                    if (g != null) {
                        cadistaAdapter.addItems(g)
                    }
                })
            }
            2 -> {
                val supervisorAdapter =
                    SupervisorAdapter(object : OnItemClickListener.SupervisorListener {
                        override fun onItemClick(s: Supervisor, view: View, position: Int) {
                            o.supervisorId = s.supervisorId
                            editTextSupervisor.setText(s.nombre)
                            dialog.dismiss()
                        }
                    })
                recyclerView.adapter = supervisorAdapter
                registroViewModel.getSupervisor().observe(this, { g ->
                    if (g != null) {
                        supervisorAdapter.addItems(g)
                    }
                })
            }
        }
    }
}
