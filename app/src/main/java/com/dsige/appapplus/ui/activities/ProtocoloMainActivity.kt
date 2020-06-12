package com.dsige.appapplus.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtCabecera
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
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
                b.getString("title")!!, b.getInt("tipo"), b.getInt("id"), b.getString("codigo")!!,
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
            }
        })

        fab.setOnClickListener {
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
            registroViewModel.validateProtocolo(o)
            Util.toastMensaje(this, "Verificando Sed...")
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
}
