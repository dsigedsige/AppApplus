package com.dsige.appapplus.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Grupo
import com.dsige.appapplus.data.local.model.InspeccionConductor
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.GrupoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_poste_2.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Poste2Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.editText1 -> dialogGroup(1, 33, "Nº Termas MT")
            R.id.editText2 -> dialogGroup(2, 34, "N° de Cond.")
            R.id.editText3 -> dialogGroup(3, 35, "Tipo de armado")
            R.id.editText27 -> dialogGroup(27, 36, "Estado de Retenida")
            R.id.editText28 -> dialogGroup(28, 36, "Estado de Retenida")
            R.id.editText35 -> dialogGroup(35, 36, "Estado de Retenida")
            R.id.editText36 -> dialogGroup(36, 36, "Estado de Retenida")
            R.id.fabSave -> formPoste2()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0
    lateinit var p: InspeccionConductor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = InspeccionConductor()
        arguments?.let {
            inspeccionId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poste_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        inspeccionViewModel.getInspeccionConductorById(inspeccionId).observe(viewLifecycleOwner, {
            if (it != null) {
                p = it
                editText1.setText(p.id_ternaMTNombre)
                editText2.setText(p.id_nCondNombre)
                editText3.setText(p.id_TipoArmadoNombre)
                editText4.setText(if (it.seccionIzq != 0.0) it.seccionIzq.toString() else null)
                editText5.setText(if (it.alturaIzq != 0.0) it.alturaIzq.toString() else null)
                editText6.setText(if (it.seccionDer != 0.0) it.seccionDer.toString() else null)
                editText7.setText(if (it.alturaDer != 0.0) it.alturaDer.toString() else null)
                editText8.setText(if (it.seccionAdel != 0.0) it.seccionAdel.toString() else null)
                editText9.setText(if (it.alturaAdel != 0.0) it.alturaAdel.toString() else null)
                editText10.setText(if (it.seccionAtras != 0.0) it.seccionAtras.toString() else null)
                editText11.setText(if (it.alturaAtras != 0.0) it.alturaAtras.toString() else null)
                editText12.setText(if (it.vanoIzq != 0.0) it.vanoIzq.toString() else null)
                editText13.setText(if (it.distanciaIzq != 0.0) it.distanciaIzq.toString() else null)
                editText14.setText(if (it.vanoDer != 0.0) it.vanoDer.toString() else null)
                editText15.setText(if (it.distanciaDer != 0.0) it.distanciaDer.toString() else null)
                editText16.setText(if (it.vanoAdel != 0.0) it.vanoAdel.toString() else null)
                editText17.setText(if (it.distanciaAdel != 0.0) it.distanciaAdel.toString() else null)
                editText18.setText(if (it.vanoAtras != 0.0) it.vanoAtras.toString() else null)
                editText19.setText(if (it.distanciaAtras != 0.0) it.distanciaAtras.toString() else null)
                editText21.setText(if (it.retIzq_1 != 0.0) it.retIzq_1.toString() else null)
                editText22.setText(if (it.retAtras_1 != 0.0) it.retAtras_1.toString() else null)
                editText23.setText(if (it.retIzq_2 != 0.0) it.retIzq_2.toString() else null)
                editText24.setText(if (it.retAtras_2 != 0.0) it.retAtras_2.toString() else null)
                editText25.setText(if (it.retIzq_3 != 0.0) it.retIzq_3.toString() else null)
                editText26.setText(if (it.retAtras_3 != 0.0) it.retAtras_3.toString() else null)
                editText27.setText(p.retIzq_EstadoNombre)
                editText28.setText(p.retAtras_EstadoNombre)
                editText29.setText(if (it.retDer_1 != 0.0) it.retDer_1.toString() else null)
                editText30.setText(if (it.retAde_1 != 0.0) it.retAde_1.toString() else null)
                editText31.setText(if (it.retDer_2 != 0.0) it.retDer_2.toString() else null)
                editText32.setText(if (it.retAde_2 != 0.0) it.retAde_2.toString() else null)
                editText33.setText(if (it.retDer_3 != 0.0) it.retDer_3.toString() else null)
                editText34.setText(if (it.retAde_3 != 0.0) it.retAde_3.toString() else null)
                editText35.setText(p.retDer_EstadoNombre)
                editText36.setText(p.retAde_EstadoNombre)
                editText37.setText(it.comentario)
            }
        })
        inspeccionViewModel.success.observe(viewLifecycleOwner, {
            Util.toastMensaje(context!!, it)
            activity!!.finish()
        })
        inspeccionViewModel.error.observe(viewLifecycleOwner, {
            Util.toastMensaje(context!!, it)
            activity!!.finish()
        })
        editText1.setOnClickListener(this)
        editText2.setOnClickListener(this)
        editText3.setOnClickListener(this)
        editText27.setOnClickListener(this)
        editText28.setOnClickListener(this)
        editText35.setOnClickListener(this)
        editText36.setOnClickListener(this)
        fabSave.setOnClickListener(this)
    }

    private fun formPoste2() {
        p.inspeccionCampoId = inspeccionId
        p.id_ternaMTNombre = editText1.text.toString()
        p.id_nCondNombre = editText2.text.toString()
        p.id_TipoArmadoNombre = editText3.text.toString()
        p.retIzq_EstadoNombre = editText27.text.toString()
        p.retAtras_EstadoNombre = editText28.text.toString()
        p.retDer_EstadoNombre = editText35.text.toString()
        p.retAde_EstadoNombre = editText36.text.toString()

        when {
            editText4.text.toString().isEmpty() -> p.seccionIzq = 0.0
            else -> p.seccionIzq = editText4.text.toString().toDouble()
        }
        when {
            editText5.text.toString().isEmpty() -> p.alturaIzq = 0.0
            else -> p.alturaIzq = editText5.text.toString().toDouble()
        }
        when {
            editText6.text.toString().isEmpty() -> p.seccionDer = 0.0
            else -> p.seccionDer = editText6.text.toString().toDouble()
        }
        when {
            editText7.text.toString().isEmpty() -> p.alturaDer = 0.0
            else -> p.alturaDer = editText7.text.toString().toDouble()
        }
        when {
            editText8.text.toString().isEmpty() -> p.seccionAdel = 0.0
            else -> p.seccionAdel = editText8.text.toString().toDouble()
        }
        when {
            editText9.text.toString().isEmpty() -> p.alturaAdel = 0.0
            else -> p.alturaAdel = editText9.text.toString().toDouble()
        }
        when {
            editText10.text.toString().isEmpty() -> p.seccionAtras = 0.0
            else -> p.seccionAtras = editText10.text.toString().toDouble()
        }
        when {
            editText11.text.toString().isEmpty() -> p.alturaAtras = 0.0
            else -> p.alturaAtras = editText11.text.toString().toDouble()
        }
        when {
            editText12.text.toString().isEmpty() -> p.vanoIzq = 0.0
            else -> p.vanoIzq = editText12.text.toString().toDouble()
        }
        when {
            editText13.text.toString().isEmpty() -> p.distanciaIzq = 0.0
            else -> p.distanciaIzq = editText13.text.toString().toDouble()
        }
        when {
            editText14.text.toString().isEmpty() -> p.vanoDer = 0.0
            else -> p.vanoDer = editText14.text.toString().toDouble()
        }
        when {
            editText15.text.toString().isEmpty() -> p.distanciaDer = 0.0
            else -> p.distanciaDer = editText15.text.toString().toDouble()
        }
        when {
            editText16.text.toString().isEmpty() -> p.vanoAdel = 0.0
            else -> p.vanoAdel = editText16.text.toString().toDouble()
        }
        when {
            editText17.text.toString().isEmpty() -> p.distanciaAdel = 0.0
            else -> p.distanciaAdel = editText17.text.toString().toDouble()
        }
        when {
            editText18.text.toString().isEmpty() -> p.vanoAtras = 0.0
            else -> p.vanoAtras = editText18.text.toString().toDouble()
        }
        when {
            editText19.text.toString().isEmpty() -> p.distanciaAtras = 0.0
            else -> p.distanciaAtras = editText19.text.toString().toDouble()
        }
        when {
            editText21.text.toString().isEmpty() -> p.retIzq_1 = 0.0
            else -> p.retIzq_1 = editText21.text.toString().toDouble()
        }
        when {
            editText22.text.toString().isEmpty() -> p.retAtras_1 = 0.0
            else -> p.retAtras_1 = editText22.text.toString().toDouble()
        }
        when {
            editText23.text.toString().isEmpty() -> p.retIzq_2 = 0.0
            else -> p.retIzq_2 = editText23.text.toString().toDouble()
        }
        when {
            editText24.text.toString().isEmpty() -> p.retAtras_2 = 0.0
            else -> p.retAtras_2 = editText24.text.toString().toDouble()
        }
        when {
            editText25.text.toString().isEmpty() -> p.retIzq_3 = 0.0
            else -> p.retIzq_3 = editText25.text.toString().toDouble()
        }
        when {
            editText26.text.toString().isEmpty() -> p.retAtras_3 = 0.0
            else -> p.retAtras_3 = editText26.text.toString().toDouble()
        }
        p.comentario = editText37.text.toString()
        inspeccionViewModel.validatePoste2(p)
    }

    private fun dialogGroup(tipo: Int, groupId: Int, title: String) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
        @SuppressLint("InflateParams") val v =
            LayoutInflater.from(context).inflate(R.layout.dialog_combo, null)
        val textViewTitulo: TextView = v.findViewById(R.id.textViewTitulo)
        val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
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

        textViewTitulo.text = title

        val grupoAdapter = GrupoAdapter(object : OnItemClickListener.GrupoListener {
            override fun onItemClick(g: Grupo, view: View, position: Int) {
                when (tipo) {
                    1 -> {
                        p.id_ternaMT = g.grupoId
                        editText1.setText(g.descripcion)
                    }
                    2 -> {
                        p.id_nCond = g.grupoId
                        editText2.setText(g.descripcion)
                    }
                    3 -> {
                        p.id_TipoArmado = g.grupoId
                        editText3.setText(g.descripcion)
                    }
                    27 -> {
                        p.retIzq_Estado = g.grupoId
                        editText27.setText(g.descripcion)
                    }
                    28 -> {
                        p.retAtras_Estado = g.grupoId
                        editText28.setText(g.descripcion)
                    }
                    35 -> {
                        p.retDer_Estado = g.grupoId
                        editText35.setText(g.descripcion)
                    }
                    36 -> {
                        p.retAde_Estado = g.grupoId
                        editText36.setText(g.descripcion)
                    }
                }
                dialog.dismiss()
            }
        })
        recyclerView.adapter = grupoAdapter
        inspeccionViewModel.getGrupoById(groupId).observe(viewLifecycleOwner, {
            grupoAdapter.addItems(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Poste2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}