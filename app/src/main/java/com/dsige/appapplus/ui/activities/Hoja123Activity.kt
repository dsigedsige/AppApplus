package com.dsige.appapplus.ui.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtHoja123
import com.dsige.appapplus.data.viewModel.HojaViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_hoja123.*
import javax.inject.Inject

class Hoja123Activity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        formHoja()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var hojaViewModel: HojaViewModel
    lateinit var o: OtHoja123
    private var tipo: Int = 0
    private var formatoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoja123)

        o = OtHoja123()

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

        showTipo(t)

        if (id != 0) {
            hojaViewModel.getHoja123ById(id).observe(this, Observer { q ->
                if (q != null) {
                    o.hoja123Id = q.hoja123Id
                    when (t) {
                        1 -> {
                            editText1.setText(q.nroCelda)
                            editText2.setText(q.funcion)
                            editText3.setText(q.cliente)
                            editText4.setText(q.suminisrro)
                        }
                        2 -> {
                            editText1.setText(q.equipo)
                            editText2.setText(q.subtipo)
                            checkTFijo.isChecked = q.tipo_Fijo == "SI"
                            checkTExtraib.isChecked = q.tipo_Extraib == "SI"
                            editText3.setText(q.kardex)
                            editText4.setText(q.nroFabrica)
                            editText5.setText(q.marca)
                            editText6.setText(q.modelo)
                            editText7.setText(q.inom)
                            editText8.setText(q.mando)
                            editText9.setText(q.rele)
                            editText10.setText(q.irele)
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

    private fun showTipo(t: Int) {
        when (t) {
            1 -> {
                layout1.hint = "Nro de Celda"
                layout2.hint = "Función"
                layout3.hint = "Enlace / Cliente"
                layout4.hint = "Suministro"
                checkTFijo.visibility = View.GONE
                checkTExtraib.visibility = View.GONE
                layout5.visibility = View.GONE
                layout6.visibility = View.GONE
                layout7.visibility = View.GONE
                layout8.visibility = View.GONE
                layout9.visibility = View.GONE
                layout10.visibility = View.GONE
            }
            2 -> {
                layout1.hint = "Equipo"
                layout2.hint = "SubTipo"
                layout3.hint = "Kardex"
                layout4.hint = "N° Fabrica"
                layout5.hint = "Marca"
                layout6.hint = "Modelo"
                layout7.hint = "Inom"
                layout8.hint = "Mando"
                layout9.hint = "Rele"
                layout10.hint = "Irele"
            }
        }
    }

    private fun formHoja() {
        o.formatoId = formatoId
        o.item = tipo
        when (tipo) {
            1 -> {
                o.nroCelda = editText1.text.toString()
                o.funcion = editText2.text.toString()
                o.cliente = editText3.text.toString()
                o.suminisrro = editText4.text.toString()
            }
            2 -> {
                o.equipo = editText1.text.toString()
                o.subtipo = editText2.text.toString()
                o.tipo_Fijo =  if(checkTFijo.isChecked) "SI" else "NO"
                o.tipo_Extraib =  if(checkTExtraib.isChecked) "SI" else "NO"
                o.kardex = editText3.text.toString()
                o.nroFabrica = editText4.text.toString()
                o.marca = editText5.text.toString()
                o.modelo = editText6.text.toString()
                o.inom = editText7.text.toString()
                o.mando = editText8.text.toString()
                o.rele = editText9.text.toString()
                o.irele = editText10.text.toString()
            }
        }
        hojaViewModel.validateHoja12(o)
    }
}