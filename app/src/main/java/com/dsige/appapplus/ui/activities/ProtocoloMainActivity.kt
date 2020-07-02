package com.dsige.appapplus.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
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
import com.dsige.appapplus.data.local.model.OtCabecera
import com.dsige.appapplus.data.local.model.Supervisor
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Gps
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.SupervisorAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_protocolo_main.*
import java.util.*
import javax.inject.Inject

class ProtocoloMainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    lateinit var o: OtCabecera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocolo_main)

        o = OtCabecera()

        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getString("title")!!,
                b.getInt("tipo"),
                b.getInt("id"),
                b.getString("codigo")!!,
                b.getInt("otId"),
                b.getInt("estado"),
                b.getInt("usuarioId")
//                b.getInt("sId"),
//                b.getString("nS")!!
            )
        }
    }

    private fun bindUI(
        title: String,
        tipo: Int,
        id: Int,
        codigo: String,
        otId: Int,
        estado: Int,
        usuarioId: Int
//        sId: Int, // supervisor Id
//        nS: String // nombre supervisor
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

        registroViewModel.getHojaById(id).observe(this, Observer { ot ->
            if (ot != null) {
                o = ot
                editTextNroSed.setText(ot.sed)
                editTextSoporte.setText(ot.soporte)
                editTextSet.setText(ot.setProtocolo)
                editTextAlimentador.setText(ot.alimentador)
                editTextCuadricula.setText(ot.cuadrilla)
                editTextLamina.setText(ot.lamina)
                editTextLetra.setText(ot.letra)
                editTextSupervisor.setText(ot.nombreSupervisor)
            }
        })

        editTextSupervisor.setOnClickListener { spinnerCombo() }

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
                    o.soporte = editTextSoporte.text.toString()
                    o.setProtocolo = editTextSet.text.toString()
                    o.alimentador = editTextAlimentador.text.toString()
                    o.cuadrilla = editTextCuadricula.text.toString()
                    o.lamina = editTextLamina.text.toString()
                    o.letra = editTextLetra.text.toString()
                    o.fechaRegistro = Util.getFecha()
                    o.usuario = usuarioId
                    o.nombreSupervisor = editTextSupervisor.text.toString()
                    registroViewModel.validateProtocolo(o)
                    Util.toastMensaje(this, "Verificando Sed...")
                }
            } else {
                gps.showSettingsAlert(this)
            }
        }

        registroViewModel.success.observe(this, Observer { s ->
            if (s != null) {
                startActivity(
                    Intent(this@ProtocoloMainActivity, ProtocoloActivity::class.java)
                        .putExtra("id", id)
                        .putExtra("otId", otId)
                        .putExtra("title", title)
                        .putExtra("tipo", tipo)
                        .putExtra("codigo", codigo)
                        .putExtra("estado", 1)
                )
                finish()
            }
        })

        registroViewModel.error.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
            }
        })
    }

    private fun spinnerCombo() {
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

        textViewTitulo.text = String.format("Supervisores")

        val supervisorAdapter = SupervisorAdapter(object : OnItemClickListener.SupervisorListener {
            override fun onItemClick(s: Supervisor, view: View, position: Int) {
                o.supervisorId = s.supervisorId
                editTextSupervisor.setText(s.nombre)
                dialog.dismiss()
            }
        })
        recyclerView.adapter = supervisorAdapter
        registroViewModel.getSupervisor().observe(this, Observer { g ->
            if (g != null) {
                supervisorAdapter.addItems(g)
            }
        })
    }
}