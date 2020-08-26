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
import com.dsige.appapplus.data.local.model.ParteDiario
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_parte_diario.*
import java.util.*
import javax.inject.Inject

class ParteDiarioActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editTextFecha -> Util.getDateDialog(this, editTextFecha)
            R.id.editTextTurno -> spinnerCombo(21, "Turno")
            R.id.editTextTrabajo -> spinnerCombo(23, "Trabajo Programado")
            R.id.editTextHInicio -> spinnerCombo(22, "Horario Inicio")
            R.id.editTextHFin -> spinnerCombo(24, "Horario Fin")
            R.id.fabGenerate -> formValidate()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    lateinit var p: ParteDiario
    private var otId: Int = 0
    private var usuarioId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parte_diario)
        val b = intent.extras
        if (b != null) {

            p = ParteDiario()

            bindUI(b.getInt("id"), b.getInt("usuarioId"), b.getInt("estadoId"))
        }
    }

    private fun bindUI(id: Int, usu: Int, e: Int) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Parte Diario"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        editTextTurno.setOnClickListener(this)
        editTextTrabajo.setOnClickListener(this)
        editTextHInicio.setOnClickListener(this)
        editTextHFin.setOnClickListener(this)
        fabGenerate.setOnClickListener(this)

        editTextFecha.setText(Util.getFechaDiaMas())
        otId = id
        usuarioId= usu

        registroViewModel.getOtById(id).observe(this, Observer { t ->
            if (t != null) {
                textViewCodigo.text = t.nroOt
                editTextDistrito.setText(t.distrito)
            }
        })

        registroViewModel.getParteDiarioByOt(id).observe(this, Observer { t ->
            if (t != null) {
                p = t
                editTextFecha.setText(t.fechaSalida)
                editTextTurno.setText(t.turno)
                editTextHInicio.setText(t.horaInicio)
                editTextHFin.setText(t.horaFin)
                editTextTrabajo.setText(t.nombreTrabajoProgramado)
                editTextSed.setText(t.nroSed)
            }
        })

        registroViewModel.error.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
            }
        })

        registroViewModel.success.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
                finish()
            }
        })
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
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
        builder.setView(v)
        val dialog = builder.create()
        dialog.show()

        textViewTitulo.text = title

        val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
            override fun onItemClick(g: Grupo, view: View, position: Int) {
                when (tipo) {
                    21 -> {
                        editTextTurno.setText(g.descripcion)
                        when (g.descripcion) {
                            "Mañana" -> {
                                editTextHInicio.setText(String.format("08:30 am"))
                                editTextHFin.setText(String.format("1:00 pm"))
                            }
                            "Tarde" -> {
                                editTextHInicio.setText(String.format("2:00 pm"))
                                editTextHFin.setText(String.format("7:00 pm"))
                            }
                        }
                    }
                    22 -> editTextHInicio.setText(g.descripcion)
                    24 -> editTextHFin.setText(g.descripcion)
                    23 -> {
                        p.trabajoProgramadoId = g.grupoId
                        editTextTrabajo.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        registroViewModel.getGrupoById(tipo).observe(this, Observer { g ->
            if (g != null) {
                grupoAdapter.addItems(g)
            }
        })
    }

    private fun formValidate() {
        p.otId = otId
        p.fechaSalida = editTextFecha.text.toString()
        p.fechaSalidaProgramada = editTextFecha.text.toString()
        p.turno = editTextTurno.text.toString()
        p.horaInicio = editTextHInicio.text.toString()
        p.horaFin = editTextHFin.text.toString()
        p.nombreTrabajoProgramado = editTextTrabajo.text.toString()
        p.nroSed = editTextSed.text.toString().toUpperCase(Locale.getDefault())
        p.proyectistaId = usuarioId
        p.usuarioId = usuarioId
        p.estado = 1
        p.active = 1
        registroViewModel.validateParteDiario(p)
    }
}