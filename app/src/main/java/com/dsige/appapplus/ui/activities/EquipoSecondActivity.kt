package com.dsige.appapplus.ui.activities

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.dsige.appapplus.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_equipo_second.*

class EquipoSecondActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_second)
        val b = intent.extras
        if (b != null) {
            bindUI(b.getInt("tipo"),b.getString("title")!!)
        }
    }

    /**
     * tipo -> 1 = Transformadores
     *         2 = Equipo de Maniobra
     *         3 = Equipo Protección
     *         4 = Equipo en Redes Areas
     */
    private fun bindUI(tipo: Int,title:String) {

        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        when (tipo) {
            0 -> {
                textViewTitle.text = String.format("Ubicación del Medidor")
                layout1.hint = "Kardex"
                layout2.hint = "Nro Fabrica"
                layout3.hint = "SED"
                layout4.hint = "CELDA"
                layout5.hint = "Potencia KVa"
                editText5.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                layout6.hint = "Año"
                layout7.hint = "Marca"
                layout8.hint = "Tipo"
                layout9.hint = "Destino"
                layout10.hint = "Observación"
            }
            1 -> {
                textViewTitle.text = String.format("Ubicación del Medidor")
                layout1.hint = "Kardex"
                layout2.hint = "Nro Fabrica"
                layout3.hint = "SED"
                layout4.hint = "CELDA"
                layout5.hint = "Funcion de Celda"
                layout6.hint = "Enlace"
                layout7.hint = "Destino"
                layout8.hint = "Observacion"
                layout9.visibility = View.GONE
                layout10.visibility = View.GONE
            }
            2 -> {
                textViewTitle.visibility = View.GONE
                layout1.hint = "Equipo"
                layout2.hint = "Nro Kardex"
                layout3.hint = "Marca"
                layout4.hint = "Tipo"
                layout5.visibility = View.GONE
                layout6.visibility = View.GONE
                layout7.visibility = View.GONE
                layout8.visibility = View.GONE
                layout9.visibility = View.GONE
                layout10.visibility = View.GONE
            }
            3 -> {
                textViewTitle.text = String.format("Ubicación")
                layout1.hint = "Kardex"
                layout2.hint = "Nro Fabrica"
                layout3.hint = "N PRC"
                layout4.hint = "Soporte"
                layout5.hint = "Destino"
                layout6.hint = "Observación"
                layout7.visibility = View.GONE
                layout8.visibility = View.GONE
                layout9.visibility = View.GONE
                layout10.visibility = View.GONE
            }
        }
        fabGenerate.setOnClickListener(this)
    }
}
