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
import com.dsige.appapplus.data.local.model.*
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.UsuarioViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Gps
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.FormatoAdapter
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.adapters.OtCabeceraAdapter
import com.dsige.appapplus.ui.adapters.SupervisorAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_formato.*
import java.net.URISyntaxException
import java.util.*
import javax.inject.Inject

class FormatoActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab1 -> goActivity(1, "Levantamiento de Campo")
            R.id.fab2 -> goActivity(2, "Hoja Equipamiento")
            R.id.fab3 -> goActivity(3, "Soporte MT")
            R.id.fab4 -> goActivity(4, "Soporte BT")
            R.id.fab5 -> goActivity(5, "Equipo Existente")
            R.id.fab6 -> goActivity(6, "Protocolo NSPT")
            R.id.editTextCombo -> spinnerCombo(estadoId)
            R.id.fabGenerate -> formOT()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    lateinit var usuarioViewModel: UsuarioViewModel

    private var otId: Int = 0
    private var formatoId: Int = 0
    private var usuarioId: Int = 0
    private var estadoId: Int = 0
//    private var supervisorId: Int = 0
//    private var nombreSupervisor: String = ""

    lateinit var builder: AlertDialog.Builder
    var dialog: AlertDialog? = null
    lateinit var builderR: AlertDialog.Builder
    var dialogR: AlertDialog? = null

    lateinit var ot: Ot
    lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formato)

        ot = Ot()

        val b = intent.extras
        if (b != null) {
            bindUI(b.getInt("id"), b.getInt("usuarioId"), b.getInt("estadoId"))
        }
    }

    private fun bindUI(id: Int, usu: Int, e: Int) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)
        usuarioViewModel =
            ViewModelProvider(this, viewModelFactory).get(UsuarioViewModel::class.java)

        otId = id
        usuarioId = usu
        estadoId = e

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Lista de Formato"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        registroViewModel.getOtById(id).observe(this, { t ->
            if (t != null) {
                ot = t
                textView1.text = t.nroOt
                textView2.text = String.format("Dia Vcto: %s", t.diasVencimiento)
                textView3.text = t.nombre
                textView4.text = t.fechaRecepcion
                textView5.text = t.fechaAsignacion
//                supervisorId = t.supervisorId
//                nombreSupervisor = t.nombre

                if (e == 6) {
                    editTextCombo.setText(t.nombreMotivoId)
                    editTextComentario.setText(t.comentario)
                }
            }
        })

        fab1.setOnClickListener(this)
        fab2.setOnClickListener(this)
        fab3.setOnClickListener(this)
        fab4.setOnClickListener(this)
        fab5.setOnClickListener(this)
        fab6.setOnClickListener(this)
        fabGenerate.setOnClickListener(this)
        editTextCombo.setOnClickListener(this)

        if (e == 6) {
            textViewCombo.hint = "Motivo de Reasignación"
            textViewComentario.visibility = View.VISIBLE
            fabGenerate.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            fab.visibility = View.GONE
            supportActionBar!!.title = "Reasignación de OT"
        } else {
            supportActionBar!!.title = "Lista de Formato"
        }

        val oTAdapter = OtCabeceraAdapter(object : OnItemClickListener.OTCabeceraListener {
            override fun onItemClick(r: OtCabecera, view: View, position: Int) {
                if (r.active == 1) {
                    when (r.tipoFormatoId) {
                        1, 2, 6 -> startActivity(
                            Intent(
                                this@FormatoActivity, when (r.tipoFormatoId) {
                                    1 -> LevantamientoMainActivity::class.java
                                    2 -> HojaMainActivity::class.java
                                    else -> ProtocoloMainActivity::class.java
                                }
                            )
                                .putExtra("id", r.formatoId)
                                .putExtra("otId", r.otId)
                                .putExtra("title", r.nombreTipoFormato)
                                .putExtra("tipo", r.tipoFormatoId)
                                .putExtra("codigo", textView1.text)
                                .putExtra("estado", 1)
                                .putExtra("usuarioId", usuarioId)
                                .putExtra("sId", r.supervisorId)
                                .putExtra("nS", r.nombreSupervisor)
                        )
                        3, 4, 5 -> generateCabecera(
                            r.nombreTipoFormato,
                            r.tipoFormatoId,
                            r.formatoId,
                            textView1.text.toString(),
                            r.otId,
                            1
//                            r.supervisorId,
//                            r.nombreSupervisor
                        )
                    }
                } else
                    usuarioViewModel.setError("Inspección Cerrada")
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = oTAdapter
        registroViewModel.getOtCabeceraByTipoPaging(id)
            .observe(this, { s ->
                if (s != null) {
                    oTAdapter.addItems(s)
                }
            })
        registroViewModel.cabecera.value = 0

        registroViewModel.getMaxIdOt().observe(this, { i ->
            formatoId = if (i != null) {
                i + 1
            } else
                1
        })

        registroViewModel.mensaje.observe(this, { s ->
            if (s != null) {
                closeDialog()
                startActivity(
                    Intent(
                        this,
                        when (s.mensaje.toInt()) {
                            1 -> PhotoActivity::class.java
                            5 -> EquipoMainActivity::class.java
                            else -> FormMainActivity::class.java
                        }
                    )
                        .putExtra("tipo", s.mensaje.toInt())
                        .putExtra("id", s.codigo)
                        .putExtra("otId", otId)
                        .putExtra(
                            "title",
                            when (s.mensaje) {
                                "1" -> "Levantamiento de Campo"
                                "3" -> "Soporte MT"
                                "4" -> "Soporte BT"
                                else -> "Equipo Existente"
                            }
                        )
                        .putExtra("codigo", textView1.text.toString())
                        .putExtra("estado", 1)
                        .putExtra("usuarioId", usuarioId)
                )
            }
        })

        usuarioViewModel.error.observe(this, { s ->
            if (s != null) {
                closeLoad()
                Util.toastMensaje(this, s)
            }
        })

        usuarioViewModel.success.observe(this, { s ->
            if (s != null) {
                sendSocket()
                closeLoad()
                Util.toastMensaje(this, s)
                finish()
            }
        })
    }

    private fun spinnerCombo(estadoId: Int) {
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

        textViewTitulo.text =
            if (estadoId == 6) String.format("Motivo de Reasignación") else String.format("Formato")

        when (estadoId) {
            6 -> {
                val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
                    override fun onItemClick(g: Grupo, view: View, position: Int) {
                        ot.motivoId = g.grupoId
                        editTextCombo.setText(g.descripcion)
                        dialog.dismiss()
                    }
                })
                recyclerView.adapter = grupoAdapter
                registroViewModel.getGrupoById(20).observe(this, { g ->
                    if (g != null) {
                        grupoAdapter.addItems(g)
                    }
                })
            }
            else -> {
                val formatoAdapter = FormatoAdapter(object : OnItemClickListener.FormatoListener {
                    override fun onItemClick(f: Formato, view: View, position: Int) {
                        editTextCombo.setText(f.nombre)
                        registroViewModel.cabecera.value = f.formatoId
                        dialog.dismiss()
                    }
                })
                recyclerView.adapter = formatoAdapter
                registroViewModel.getFormato().observe(this, { p ->
                    if (p != null) {
                        formatoAdapter.addItems(p)
                    }
                })
            }
        }
    }

    private fun goActivity(i: Int, title: String) {
        when (i) {
            1, 2, 6 -> startActivity(
                Intent(
                    this, when (i) {
                        1 -> LevantamientoMainActivity::class.java
                        2 -> HojaMainActivity::class.java
                        else -> ProtocoloMainActivity::class.java
                    }
                )
                    .putExtra("tipo", i)
                    .putExtra("id", formatoId)
                    .putExtra("otId", otId)
                    .putExtra("title", title)
                    .putExtra("codigo", textView1.text.toString())
                    .putExtra("estado", 0)
                    .putExtra("usuarioId", usuarioId)
//                    .putExtra("sId", supervisorId)
//                    .putExtra("nS", nombreSupervisor)
            )
            3, 4, 5 -> generateCabecera(
                title,
                i,
                formatoId,
                textView1.text.toString(),
                otId,
                0
            )//,supervisorId,nombreSupervisor)
        }
    }

    private fun closeDialog() {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }
    }

    private fun load() {
        builderR = AlertDialog.Builder(ContextThemeWrapper(this@FormatoActivity, R.style.AppTheme))
        @SuppressLint("InflateParams") val view =
            LayoutInflater.from(this@FormatoActivity).inflate(R.layout.dialog_login, null)
        builderR.setView(view)
        val textViewTitle: TextView = view.findViewById(R.id.textView)
        textViewTitle.text = String.format("Enviando...")
        dialogR = builderR.create()
        dialogR!!.setCanceledOnTouchOutside(false)
        dialogR!!.setCancelable(false)
        dialogR!!.show()
    }

    private fun closeLoad() {
        if (dialogR != null) {
            if (dialogR!!.isShowing) {
                dialogR!!.dismiss()
            }
        }
    }

    private fun formOT() {
        ot.comentario = editTextComentario.text.toString()
        ot.nombreMotivoId = editTextCombo.text.toString()
        sendOt(ot)
    }

    private fun sendOt(ot: Ot) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Mensaje")
            .setMessage("Deseas Reasignar Ot ?")
            .setPositiveButton("SI") { dialog, _ ->
                load()
                usuarioViewModel.updateOtReasignacion(ot, true)
                dialog.dismiss()
            }
            .setNegativeButton("Guardar") { dialog, _ ->
                ot.active = 1
                usuarioViewModel.updateOtReasignacion(ot, false)
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun sendSocket() {
        try {
            socket = IO.socket(Util.urlSocket)
            val movil = Notificacion(1,"Envio a movil",ot.coordinadorId,"Proyectista")
            socket.emit("Notificacion_movil_OT", Gson().toJson(movil))
            val web = String.format("%s|%s|%s|%s",1,ot.coordinadorId,"Mensaje de Reasignación","Proyectista")
            socket.emit("Notificacion_movil_web_OT", web)
        } catch (e: URISyntaxException) {
        }
        socket.connect()
    }
    
    private fun generateCabecera(
        title: String,
        tipo: Int,
        id: Int,
        codigo: String,
        otId: Int,
        estado: Int
//        sId: Int,
//        nS: String
    ) {
        builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AppTheme))
        @SuppressLint("InflateParams") val v =
            LayoutInflater.from(this).inflate(R.layout.dialog_cabecera, null)

        val textViewTitulo: TextView = v.findViewById(R.id.textViewTitulo)
        val editTextSupervisor: TextInputEditText = v.findViewById(R.id.editTextSupervisor)
        val editTextSetAlim: TextInputEditText = v.findViewById(R.id.editTextSetAlim)
        val editTextSed: TextInputEditText = v.findViewById(R.id.editTextSed)
        val buttonSave: MaterialButton = v.findViewById(R.id.buttonSave)

        builder.setView(v)
        dialog = builder.create()
        dialog!!.show()

        var o = OtCabecera()

        registroViewModel.getOtCabeceraById(id).observe(this, { e ->
            if (e != null) {
                o = e
                editTextSetAlim.setText(o.alimentador)
                editTextSed.setText(o.sed)
            }
        })

        textViewTitulo.text = title

        editTextSupervisor.setOnClickListener {
            spinnerSupervisor(editTextSupervisor, o)
        }

        buttonSave.setOnClickListener {
            val gps = Gps(this)
            if (gps.isLocationEnabled()) {
                if (gps.latitude.toString() != "0.0" || gps.longitude.toString() != "0.0") {
                    o.formatoId = id
                    o.tipoFormatoId = tipo
                    o.nombreTipoFormato = title
                    o.nroOt = codigo
                    o.otId = otId
                    o.active = 1
                    o.coordenadaX = gps.latitude.toString()
                    o.coordenadaY = gps.longitude.toString()
                    o.sed = editTextSed.text.toString().toUpperCase(Locale.getDefault())
                    o.fechaRegistro = Util.getFecha()
                    o.usuario = usuarioId
                    o.nombreSupervisor = editTextSupervisor.text.toString()
                    registroViewModel.validateCabeceraMTBTEquipo(o)
                    Util.toastMensaje(this, "Verificando Sed...")
                }
            } else {
                gps.showSettingsAlert(this)
            }
        }
    }

    private fun spinnerSupervisor(editText: TextInputEditText, o: OtCabecera) {
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
                editText.setText(s.nombre)
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