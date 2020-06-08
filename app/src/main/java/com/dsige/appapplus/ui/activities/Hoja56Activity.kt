package com.dsige.appapplus.ui.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtHoja56
import com.dsige.appapplus.data.viewModel.HojaViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_hoja56.*
import javax.inject.Inject

class Hoja56Activity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        formHoja()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var hojaViewModel: HojaViewModel
    lateinit var o: OtHoja56
    private var tipo: Int = 0
    private var formatoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoja56)

        o = OtHoja56()

        val b = intent.extras
        if (b != null) {
            bindUI(b.getString("title")!!, b.getInt("tipo"), b.getInt("formatoId"), b.getInt("id"))
        }
    }

    private fun bindUI(title: String, t: Int, f: Int, id: Int) {
        hojaViewModel =
            ViewModelProvider(this, viewModelFactory).get(HojaViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        tipo = t
        formatoId = f

        when (t) {
            5 -> textViewTitle.text = String.format("Tablero SP")
            6 -> textViewTitle.text = String.format("Tablero AP")
            else -> {
                textViewTitle.visibility = View.GONE
                layoutCheck.visibility = View.GONE
                layoutCombo.visibility = View.GONE
                layout1.hint = "Nro Medidor"
                layout2.hint = "Foto Célula"
                layout3.hint = "Contactor"
                layout4.hint = "Int. Horario"
            }
        }

        if (id != 0) {
            hojaViewModel.getHoja56ById(id).observe(this, Observer { q ->
                if (q != null) {
                    o.hoja56Id = q.hoja56Id
                    when (t) {
                        5, 6 -> {
                            when (q.tipoTablero) {
                                1 -> chkAereo.isChecked = true
                                2 -> chkNivel.isChecked = true
                            }
                            editText1.setText(q.nroMedidor)
                            editTextTipo.setText(q.idtipo.toString())
                            editText2.setText(q.baseMovil)
                            editText3.setText(q.fusible)
                            editText4.setText(q.seccion)
                            editText5.setText(q.observacion)
                        }
                        7 -> {
                            editText1.setText(q.nroMedidor)
                            editText2.setText(q.fotocelula)
                            editText3.setText(q.fusible)
                            editText4.setText(q.seccion)
                            editText5.setText(q.observacion)
                        }
                    }
                }
            })
        }

        fab.setOnClickListener(this)

        hojaViewModel.success.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
                finish()
            }
        })

        hojaViewModel.error.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
            }
        })
    }

    private fun formHoja() {
        o.formatoId = formatoId
        o.item = tipo

        when (tipo) {
            5, 6 -> {
                o.tipoTablero = when {
                    chkAereo.isChecked -> 1
                    chkNivel.isChecked -> 2
                    else -> 0
                }
                o.nroMedidor = editText1.text.toString()
                o.baseMovil = editText2.text.toString()
                o.fusible = editText3.text.toString()
                o.seccion = editText4.text.toString()
                o.observacion = editText5.text.toString()
            }
            7 -> {
                o.nroMedidor = editText1.text.toString()
                o.fotocelula = editText2.text.toString()
                o.fusible = editText3.text.toString()
                o.seccion = editText4.text.toString()
                o.observacion = editText5.text.toString()
            }
        }
        hojaViewModel.validateHoja56(o)
    }
}