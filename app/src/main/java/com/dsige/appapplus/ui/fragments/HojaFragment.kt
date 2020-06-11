package com.dsige.appapplus.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtHoja123
import com.dsige.appapplus.data.local.model.OtHoja4
import com.dsige.appapplus.data.local.model.OtHoja56
import com.dsige.appapplus.data.viewModel.HojaViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.activities.Hoja123Activity
import com.dsige.appapplus.ui.activities.Hoja4Activity
import com.dsige.appapplus.ui.activities.Hoja56Activity
import com.dsige.appapplus.ui.adapters.OtHojaAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hoja.*
import java.util.stream.Collectors.toList
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HojaFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var hojaViewModel: HojaViewModel
    private var formatoId: Int = 0
    private var item: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            formatoId = it.getInt(ARG_PARAM1)
            item = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hoja, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        hojaViewModel =
            ViewModelProvider(this, viewModelFactory).get(HojaViewModel::class.java)

        val hojaAdapter = OtHojaAdapter(item, object : OnItemClickListener.OTHojaListener {
            override fun onItemClick123(d: OtHoja123, view: View, position: Int) {
                startActivity(
                    Intent(context, Hoja123Activity::class.java)
                        .putExtra("tipo", d.item)
                        .putExtra("formatoId", d.formatoId)
                        .putExtra("id", d.hoja123Id)
                        .putExtra(
                            "title", when (d.item) {
                                1 -> "Celda 10KV"
                                2 -> "Equipo 10KV"
                                else -> "Protección"
                            }
                        )
                )
            }

            override fun onItemClick4(d: OtHoja4, view: View, position: Int) {
                startActivity(
                    Intent(context, Hoja4Activity::class.java)
                        .putExtra("formatoId", d.formatoId)
                        .putExtra("id", d.hoja4Id)
                        .putExtra("title", "Transformadores")
                )
            }

            override fun onItemClick56(d: OtHoja56, view: View, position: Int) {
                startActivity(
                    Intent(context, Hoja56Activity::class.java)
                        .putExtra("tipo", d.item)
                        .putExtra("formatoId", d.formatoId)
                        .putExtra("id", d.hoja56Id)
                        .putExtra(
                            "title", when (d.item) {
                                5 -> "Servicio Particular"
                                else -> "Alumbrado Publico"
                            }
                        )
                )
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = hojaAdapter

        hojaViewModel.getHojaByTipo(item, formatoId).observe(viewLifecycleOwner, Observer { e ->
            if (e.isNotEmpty()) {
                hojaAdapter.addItems(e)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            HojaFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}
