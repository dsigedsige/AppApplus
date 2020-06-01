package com.dsige.appapplus.ui.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtHoja4
import com.dsige.appapplus.data.viewModel.HojaViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_hoja4.*
import javax.inject.Inject

class Hoja4Activity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        formHoja()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var hojaViewModel: HojaViewModel
    lateinit var o: OtHoja4
    private var tipo: Int = 0
    private var formatoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoja4)

        o = OtHoja4()

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

        if (id != 0) {
            hojaViewModel.getHoja4ById(id).observe(this, Observer { q ->
                if (q != null) {
                    o.hoja4Id = q.hoja4Id
                    editText1.setText(q.ubicacion)
                    editText2.setText(q.nroFabrica)
                    editText3.setText(q.potInst)
                    editText4.setText(q.anio)
                    editText5.setText(q.marca)
                    editText6.setText(q.tipo)
                    editText7.setText(q.kardex)
                    editText8.setText(q.relTransf)
                    editText9.setText(q.potenciaCC)
                    editText10.setText(q.nroFases)
                    editText11.setText(q.cableC1)
                    editText12.setText(q.cableC2)
                    editText13.setText(q.cableC3)
                    editText14.setText(q.cableC4)
                    editText15.setText(q.cableC5)
                    editText16.setText(q.disMarca)
                    editText17.setText(q.disKardex)
                    editText18.setText(q.disSerie)
                    editText19.setText(q.disIA)
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
        o.ubicacion = editText1.text.toString()
        o.nroFabrica = editText2.text.toString()
        o.potInst = editText3.text.toString()
        o.anio = editText4.text.toString()
        o.marca = editText5.text.toString()
        o.tipo = editText6.text.toString()
        o.kardex = editText7.text.toString()
        o.relTransf = editText8.text.toString()
        o.potenciaCC = editText9.text.toString()
        o.nroFases = editText10.text.toString()
        o.cableC1 = editText11.text.toString()
        o.cableC2 = editText12.text.toString()
        o.cableC3 = editText13.text.toString()
        o.cableC4 = editText14.text.toString()
        o.cableC5 = editText15.text.toString()
        o.disMarca = editText16.text.toString()
        o.disKardex = editText17.text.toString()
        o.disSerie = editText18.text.toString()
        o.disIA = editText19.text.toString()
        hojaViewModel.validateHoja4(o)
    }
}
