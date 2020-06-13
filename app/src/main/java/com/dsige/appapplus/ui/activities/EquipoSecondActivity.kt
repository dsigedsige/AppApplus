package com.dsige.appapplus.ui.activities

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtEquipo
import com.dsige.appapplus.data.viewModel.EquipoViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_equipo_second.*
import javax.inject.Inject

class EquipoSecondActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        formEquipo()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var equipoViewModel: EquipoViewModel
    lateinit var e: OtEquipo
    private var tipo: Int = 0
    private var formatoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_second)

        e = OtEquipo()

        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getInt("tipo"),
                b.getInt("formatoId"),
                b.getInt("equipoId"),
                b.getString("title")!!
            )
        }
    }

    /**
     * t -> 1 = Transformadores
     *      2 = Equipo de Maniobra
     *      3 = Equipo Protección
     *      4 = Equipo en Redes Areas
     */
    private fun bindUI(t: Int, f: Int, id: Int, title: String) {

        equipoViewModel =
            ViewModelProvider(this, viewModelFactory).get(EquipoViewModel::class.java)

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
            equipoViewModel.getEquipoById(id).observe(this, Observer { q ->
                if (q != null) {
                    e.equipoId = q.equipoId
                    when (t) {
                        1 -> {
                            editText1.setText(q.nroKardex)
                            editText2.setText(q.nroFabrica)
                            editText3.setText(q.sedUbicacion)
                            editText4.setText(q.celdaUbicacion)
                            editText5.setText(q.potenciaKVA)
                            editText6.setText(q.anio)
                            editText7.setText(q.marca)
                            editText8.setText(q.tipo)
                            editText9.setText(q.destino)
                            editText10.setText(q.observacion)
                        }
                        2 -> {
                            editText1.setText(q.nroKardex)
                            editText2.setText(q.nroFabrica)
                            editText3.setText(q.sedUbicacion)
                            editText4.setText(q.celdaUbicacion)
                            editText5.setText(q.funcionCelda)
                            editText6.setText(q.enlace)
                            editText7.setText(q.destino)
                            editText8.setText(q.observacion)
                        }
                        3 -> {
                            editText1.setText(q.equipo)
                            editText2.setText(q.nroKardex)
                            editText3.setText(q.marca)
                            editText4.setText(q.tipo)
                        }
                        4 -> {
                            editText1.setText(q.nroKardex)
                            editText2.setText(q.nroFabrica)
                            editText3.setText(q.nroPrc)
                            editText4.setText(q.soporte)
                            editText5.setText(q.destino)
                            editText6.setText(q.observacion)
                        }
                    }
                }
            })
        }

        fabGenerate.setOnClickListener(this)

        equipoViewModel.success.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
                finish()
            }
        })

        equipoViewModel.error.observe(this, Observer { s ->
            if (s != null) {
                Util.toastMensaje(this, s)
            }
        })
    }

    private fun showTipo(t: Int) {
        when (t) {
            1 -> {
                textViewTitle.text = String.format("Ubicación del Medidor")
                layout1.hint = "Kardex"
                layout2.hint = "Nro Fabrica"
                layout3.hint = "SED"
                layout4.hint = "CELDA"
                layout5.hint = "Potencia KVa"
//                editText5.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                layout6.hint = "Año"
                layout7.hint = "Marca"
                layout8.hint = "Tipo"
                layout9.hint = "Destino"
                layout10.hint = "Observación"
            }
            2 -> {
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
            3 -> {
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
            4 -> {
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
    }

    private fun formEquipo() {
        e.formatoId = formatoId
        e.tipoEquipo = tipo
        e.fecha = Util.getFecha()
        when (tipo) {
            1 -> {
                e.nroKardex = editText1.text.toString()
                e.nroFabrica = editText2.text.toString()
                e.sedUbicacion = editText3.text.toString()
                e.celdaUbicacion = editText4.text.toString()
                e.potenciaKVA = editText5.text.toString()
                e.anio = editText6.text.toString()
                e.marca = editText7.text.toString()
                e.tipo = editText8.text.toString()
                e.destino = editText9.text.toString()
                e.observacion = editText10.text.toString()
            }
            2 -> {
                e.nroKardex = editText1.text.toString()
                e.nroFabrica = editText2.text.toString()
                e.sedUbicacion = editText3.text.toString()
                e.celdaUbicacion = editText4.text.toString()
                e.funcionCelda = editText5.text.toString()
                e.enlace = editText6.text.toString()
                e.destino = editText7.text.toString()
                e.observacion = editText8.text.toString()
            }
            3 -> {
                e.equipo = editText1.text.toString()
                e.nroKardex = editText2.text.toString()
                e.marca = editText3.text.toString()
                e.tipo = editText4.text.toString()
            }
            4 -> {
                e.nroKardex = editText1.text.toString()
                e.nroFabrica = editText2.text.toString()
                e.nroPrc = editText3.text.toString()
                e.soporte = editText4.text.toString()
                e.destino = editText5.text.toString()
                e.observacion = editText6.text.toString()
            }
        }
        equipoViewModel.validateEquipo(e)
    }
}