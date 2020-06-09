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
import kotlinx.android.synthetic.main.activity_hoja_main.*
import javax.inject.Inject

class HojaMainActivity : DaggerAppCompatActivity() {

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
            if (ot != null){
                o = ot
                editTextNroSed.setText(ot.sed)
                editTextAlimentador.setText(ot.alimentador)
                if (ot.convencional == "1") chkConvencional.isChecked else !chkConvencional.isChecked
                if (ot.compacta == "1") chkCompacta.isChecked else !chkCompacta.isChecked
                if (ot.aerea == "1") chkAerea.isChecked else !chkAerea.isChecked
                if (ot.pmi == "1") chkPMI.isChecked else !chkPMI.isChecked
                if (ot.aNivel == "1") chkAnivel.isChecked else !chkAnivel.isChecked
                if (ot.pedestal == "1") chkPedestal.isChecked else !chkPedestal.isChecked
                if (ot.monoposte == "1") chkMonoPoste.isChecked else !chkMonoPoste.isChecked
                if (ot.reCloser == "1") chkRecloser.isChecked else !chkRecloser.isChecked
                if (ot.subTerranea == "1") chkSubTerranea.isChecked else !chkSubTerranea.isChecked
                if (ot.convencional == "1") chkBoveda.isChecked else !chkBoveda.isChecked
                if (ot.biposte == "1") chkBiposte.isChecked else !chkBiposte.isChecked
                if (ot.sbc == "1") chkSBC.isChecked else !chkSBC.isChecked
            }
        })

        fab.setOnClickListener {
            o.formatoId = id
            o.tipoFormatoId = tipo
            o.nombreTipoFormato = title
            o.nroOt = codigo
            o.otId = otId
            o.active = 1
            o.sed = editTextNroSed.text.toString()
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

            registroViewModel.validateHoja(o)
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
    }
}