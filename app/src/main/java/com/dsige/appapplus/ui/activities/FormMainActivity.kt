package com.dsige.appapplus.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtDetalle
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.OtDetalleAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_form_main.*
import javax.inject.Inject

class FormMainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    private var detalleId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_main)
        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getString("title")!!, b.getInt("tipo"),
                b.getInt("id"), b.getString("codigo")!!,
                b.getInt("otId"), b.getInt("estado")
            )
        }
    }

    private fun bindUI(title: String, tipo: Int, id: Int, codigo: String, otId: Int, estado: Int) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        fab.setOnClickListener {
            startActivity(
                Intent(this, FormSecondActivity::class.java)
                    .putExtra("title", title)
                    .putExtra("id", id)
                    .putExtra("detalleId", detalleId)
                    .putExtra("codigo", codigo)
                    .putExtra("tipo", tipo)
            )
        }

        val oTDetalleAdapter = OtDetalleAdapter(object : OnItemClickListener.OTDetalleListener {
            override fun onItemClick(d: OtDetalle, view: View, position: Int) {
                startActivity(
                    Intent(this@FormMainActivity, FormSecondActivity::class.java)
                        .putExtra("detalleId", d.formatoDetalleId)
                        .putExtra("id", d.formatoId)
                        .putExtra("title", title)
                        .putExtra("codigo", codigo)
                        .putExtra("tipo", tipo)
                )
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = oTDetalleAdapter

        registroViewModel.getOtCabeceraById(id).observe(this, Observer { i ->
            if (i != null) {
                textView1.text = i.formatoId.toString()
                textView2.text = i.nroOt
                textView3.text = i.alimentador
                textView4.text = i.modulo
                textView5.text = String.format("Fecha Registro : %s", i.fechaRegistro)
            }
        })

        registroViewModel.getOtDetalleById(id)
            .observe(this, Observer { s ->
                if (s != null) {
                    oTDetalleAdapter.addItems(s)
                }
            })

        if (estado == 0) {
            registroViewModel.generateCabecera(title, tipo, codigo, id, otId)
        }

        registroViewModel.getMaxIdOtDetalle().observe(this, Observer { i ->
            detalleId = if (i != null) {
                i + 1
            } else
                1
        })

        registroViewModel.success.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
            }
        })
    }
}
