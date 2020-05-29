package com.dsige.appapplus.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtEquipo
import com.dsige.appapplus.data.viewModel.EquipoViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.activities.EquipoSecondActivity
import com.dsige.appapplus.ui.adapters.OtEquipoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_equipo.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class EquipoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var equipoViewModel: EquipoViewModel
    private var tipo: Int = 0
    private var formatoId: Int = 0
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tipo = it.getInt(ARG_PARAM1)
            formatoId = it.getInt((ARG_PARAM2))
            title = it.getString((ARG_PARAM3))!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_equipo, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        equipoViewModel =
            ViewModelProvider(this, viewModelFactory).get(EquipoViewModel::class.java)

        val equipoAdapter = OtEquipoAdapter(object : OnItemClickListener.OTEquipoListener {
            override fun onItemClick(r: OtEquipo, view: View, position: Int) {
                startActivity(
                    Intent(context, EquipoSecondActivity::class.java)
                        .putExtra("tipo", r.tipoEquipo)
                        .putExtra("formatoId", r.formatoId)
                        .putExtra("equipoId", r.equipoId)
                        .putExtra("title", title)
                )
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = equipoAdapter

        equipoViewModel.getEquipoByTipo(tipo, formatoId).observe(viewLifecycleOwner, Observer { e ->
            if (e.isNotEmpty()) {
                equipoAdapter.addItems(e)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int, param3: String) =
            EquipoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}