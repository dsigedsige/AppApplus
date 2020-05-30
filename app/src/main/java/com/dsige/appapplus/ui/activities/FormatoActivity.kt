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
import com.dsige.appapplus.data.local.model.Formato
import com.dsige.appapplus.data.local.model.OtCabecera
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.adapters.FormatoAdapter
import com.dsige.appapplus.ui.adapters.OtCabeceraAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_formato.*
import javax.inject.Inject

class FormatoActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab1 -> goActivity(1, "Levantamiento de Campo")
            R.id.fab2 -> goActivity(2, "Hoja Equipamiento")
            R.id.fab3 -> goActivity(3, "Soporte BT")
            R.id.fab4 -> goActivity(4, "Soporte MT")
            R.id.fab5 -> goActivity(5, "Equipo Existente")
            R.id.fab6 -> goActivity(6, "Protocolo NSPT")
            R.id.editTextCombo -> spinnerCombo()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    private var otId: Int = 0
    private var formatoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formato)
        val b = intent.extras
        if (b != null) {
            bindUI(b.getInt("id"))
        }
    }

    private fun bindUI(id: Int) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)

        otId = id

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Lista de Formato"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        registroViewModel.getOtById(id).observe(this, Observer { t ->
            if (t != null) {
                textView1.text = t.nroOt
                textView2.text = t.estado
                textView3.text = t.nombre
                textView4.text = t.fechaRecepcion
                textView5.text = String.format("Dia Vcto: %s", t.diasVencimiento)
            }
        })

        fab1.setOnClickListener(this)
        fab2.setOnClickListener(this)
        fab3.setOnClickListener(this)
        fab4.setOnClickListener(this)
        fab5.setOnClickListener(this)
        fab6.setOnClickListener(this)
        editTextCombo.setOnClickListener(this)

        val oTAdapter = OtCabeceraAdapter(object : OnItemClickListener.OTCabeceraListener {
            override fun onItemClick(r: OtCabecera, view: View, position: Int) {
                when (r.tipoFormatoId) {
                    3, 4 -> startActivity(
                        Intent(this@FormatoActivity, FormMainActivity::class.java)
                            .putExtra("id", r.formatoId)
                            .putExtra("otId", r.otId)
                            .putExtra("title", r.nombreTipoFormato)
                            .putExtra("tipo", r.tipoFormatoId)
                            .putExtra("codigo", textView1.text)
                            .putExtra("estado", 1)
                    )
                    5 -> startActivity(
                        Intent(this@FormatoActivity, EquipoMainActivity::class.java)
                            .putExtra("id", r.formatoId)
                            .putExtra("otId", r.otId)
                            .putExtra("title", r.nombreTipoFormato)
                            .putExtra("tipo", r.tipoFormatoId)
                            .putExtra("codigo", textView1.text)
                            .putExtra("estado", 1)
                    )
                    6-> startActivity(
                        Intent(this@FormatoActivity, ProtocoloActivity::class.java)
                            .putExtra("id", r.formatoId)
                            .putExtra("otId", r.otId)
                            .putExtra("title", r.nombreTipoFormato)
                            .putExtra("tipo", r.tipoFormatoId)
                            .putExtra("codigo", textView1.text)
                            .putExtra("estado", 1)
                    )
                }
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = oTAdapter
        registroViewModel.getOtCabeceraByTipoPaging(id)
            .observe(this, Observer { s ->
                if (s != null) {
                    oTAdapter.addItems(s)
                }
            })
        registroViewModel.cabecera.value = 0


        registroViewModel.getMaxIdOt().observe(this, Observer { i ->
            formatoId = if (i != null) {
                i + 1
            } else
                1
        })

//        val b = FloatingActionButton(this)
//        b.title = "Camara"
//        b.colorNormal = ContextCompat.getColor(this, R.color.colorWhite)
//        b.colorPressed = ContextCompat.getColor(this, R.color.white_pressed)
//        b.setIconDrawable(resources.getDrawable(R.drawable.ic_camera,resources.newTheme()))
//        fab.addButton(b)
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

        textViewTitulo.text = String.format("Formato")

        val formatoAdapter = FormatoAdapter(object : OnItemClickListener.FormatoListener {
            override fun onItemClick(f: Formato, view: View, position: Int) {
                editTextCombo.setText(f.nombre)
                registroViewModel.cabecera.value = f.formatoId
                dialog.dismiss()
            }
        })
        recyclerView.adapter = formatoAdapter
        registroViewModel.getFormato().observe(this, Observer { p ->
            if (p != null) {
                formatoAdapter.addItems(p)
            }
        })
    }

    private fun goActivity(i: Int, title: String) {
        when (i) {
            3, 4 -> startActivity(
                Intent(this, FormMainActivity::class.java)
                    .putExtra("tipo", i)
                    .putExtra("id", formatoId)
                    .putExtra("otId", otId)
                    .putExtra("title", title)
                    .putExtra("codigo", textView1.text.toString())
                    .putExtra("estado", 0)
            )
            5 -> startActivity(
                Intent(this, EquipoMainActivity::class.java)
                    .putExtra("tipo", i)
                    .putExtra("id", formatoId)
                    .putExtra("otId", otId)
                    .putExtra("title", title)
                    .putExtra("codigo", textView1.text.toString())
                    .putExtra("estado", 0)
            )
            6->startActivity(
                Intent(this, ProtocoloActivity::class.java)
                    .putExtra("tipo", i)
                    .putExtra("id", formatoId)
                    .putExtra("otId", otId)
                    .putExtra("title", title)
                    .putExtra("codigo", textView1.text.toString())
                    .putExtra("estado", 0)
            )
        }
    }
}