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
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.adapters.SupervisorAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_hoja_main.*
import java.util.*
import javax.inject.Inject

class HojaMainActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editTextCombo -> spinnerCombo(1, "Ubicación de la sed proyectada")
            R.id.editTextSupervisor -> spinnerCombo(2, "Supervisores")
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    lateinit var o: OtCabecera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoja_main)

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
                chkConvencional.isChecked = ot.convencional == "1"
                chkCompacta.isChecked = ot.compacta == "1"
                chkAerea.isChecked = ot.aerea == "1"
                chkPMI.isChecked = ot.pmi == "1"
                chkAnivel.isChecked = ot.aNivel == "1"
                chkPedestal.isChecked = ot.pedestal == "1"
                chkMonoPoste.isChecked = ot.monoposte == "1"
                chkRecloser.isChecked = ot.reCloser == "1"
                chkSubTerranea.isChecked = ot.subTerranea == "1"
                chkBoveda.isChecked = ot.convencional == "1"
                chkBiposte.isChecked = ot.biposte == "1"
                chkSBC.isChecked = ot.sbc == "1"
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

                    o.convencional = if (chkConvencional.isChecked) "1" else "0"
                    o.compacta = if (chkCompacta.isChecked) "1" else "0"
                    o.aerea = if (chkAerea.isChecked) "1" else "0"
                    o.pmi = if (chkPMI.isChecked) "1" else "0"
                    o.aNivel = if (chkAnivel.isChecked) "1" else "0"
                    o.pedestal = if (chkPedestal.isChecked) "1" else "0"
                    o.monoposte = if (chkMonoPoste.isChecked) "1" else "0"
                    o.reCloser = if (chkRecloser.isChecked) "1" else "0"
                    o.subTerranea = if (chkSubTerranea.isChecked) "1" else "0"
                    o.boveda = if (chkBoveda.isChecked) "1" else "0"
                    o.biposte = if (chkBiposte.isChecked) "1" else "0"
                    o.sbc = if (chkSBC.isChecked) "1" else "0"
                    o.usuario = usuarioId
                    o.fechaRegistro = Util.getFecha()
                    o.nombreUbicacionSed = editTextCombo.text.toString()
                    o.nombreSupervisor = editTextSupervisor.text.toString()
                    registroViewModel.validateHoja(o)
                    Util.toastMensaje(this, "Verificando...")
                }
            } else {
                gps.showSettingsAlert(this)
            }
        }

        registroViewModel.success.observe(this, Observer { s ->
            if (s != null) {
                startActivity(
                    Intent(this@HojaMainActivity, HojaActivity::class.java)
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
                val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
                    override fun onItemClick(g: Grupo, view: View, position: Int) {
                        o.ubicacionSed = g.grupoId.toString()
                        editTextCombo.setText(g.descripcion)
                        dialog.dismiss()
                    }
                })
                recyclerView.adapter = grupoAdapter
                registroViewModel.getGrupoById(19).observe(this, Observer { g ->
                    if (g != null) {
                        grupoAdapter.addItems(g)
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
                registroViewModel.getSupervisor().observe(this, Observer { g ->
                    if (g != null) {
                        supervisorAdapter.addItems(g)
                    }
                })
            }
        }
    }
}