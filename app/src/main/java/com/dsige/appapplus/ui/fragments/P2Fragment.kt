package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager

import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtProtocolo
import com.dsige.appapplus.data.viewModel.ProtocoloViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_p2.*
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class P2Fragment : DaggerFragment(), View.OnClickListener {

    override fun onClick(v: View) {
        formProtocolo()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var protocoloViewModel: ProtocoloViewModel
    private lateinit var pager: ViewPager
    lateinit var o: OtProtocolo
    private var formatoId: Int = 0
    private var tipoProtocolo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o = OtProtocolo()

        arguments?.let {
            formatoId = it.getInt(ARG_PARAM1)
            tipoProtocolo = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_p2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager = activity!!.findViewById(R.id.viewPager)
        bindUI()
    }

    private fun bindUI() {
        protocoloViewModel =
            ViewModelProvider(this, viewModelFactory).get(ProtocoloViewModel::class.java)

        fabGenerate.setOnClickListener(this)

        protocoloViewModel.getProtocoloByTipo(formatoId, tipoProtocolo)
            .observe(viewLifecycleOwner, Observer { p ->
                if (p != null) {
                    o.protocoloId = p.protocoloId
                    editTextRQHM1.setText(p.rBt1)
                    editTextPozoBT1.setText(p.pBt1)
                    editTextRQHM2.setText(p.rBt2)
                    editTextPozoBT2.setText(p.pBt2)
                    editTextRQHM3.setText(p.rBt3)
                    editTextPozoBT3.setText(p.pBt3)
                    editTextRQHM4.setText(p.rBt4)
                    editTextPozoBT4.setText(p.pBt4)
                    editTextRQHM5.setText(p.rBtE)
                    editTextEquivalenteBT.setText(p.pBtE)
                    editTextRQHM6.setText(p.rMt1)
                    editTextPozoMT1.setText(p.pMt1)
                    editTextRQHM7.setText(p.rMt2)
                    editTextPozoMT2.setText(p.pMt2)
                    editTextRQHM8.setText(p.rMt3)
                    editTextPozoMT3.setText(p.pMt3)
                    editTextRQHM9.setText(p.rMt4)
                    editTextPozoMT4.setText(p.pMt4)
                    editTextRQHM10.setText(p.rMtE)
                    editTextEquivalenteMT.setText(p.pMtE)
                    editTextObservacion.setText(p.observaciones)
                }
            })
        protocoloViewModel.success.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
                pager.currentItem = 2
            }
        })

        protocoloViewModel.error.observe(viewLifecycleOwner, Observer { s ->
            if (s != null) {
                Util.toastMensaje(context!!, s)
            }
        })
    }

    private fun formProtocolo() {
        o.formatoId = formatoId
        o.tipoProtocoloId = tipoProtocolo
        o.rBt1 = editTextRQHM1.text.toString()
        o.pBt1 = editTextPozoBT1.text.toString()
        o.rBt2 = editTextRQHM2.text.toString()
        o.pBt2 = editTextPozoBT2.text.toString()
        o.rBt3 = editTextRQHM3.text.toString()
        o.pBt3 = editTextPozoBT3.text.toString()
        o.rBt4 = editTextRQHM4.text.toString()
        o.pBt4 = editTextPozoBT4.text.toString()
        o.rBtE = editTextRQHM5.text.toString()
        o.pBtE = editTextEquivalenteBT.text.toString()
        o.rMt1 = editTextRQHM6.text.toString()
        o.pMt1 = editTextPozoMT1.text.toString()
        o.rMt2 = editTextRQHM7.text.toString()
        o.pMt2 = editTextPozoMT2.text.toString()
        o.rMt3 = editTextRQHM8.text.toString()
        o.pMt3 = editTextPozoMT3.text.toString()
        o.rMt4 = editTextRQHM9.text.toString()
        o.pMt4 = editTextPozoMT4.text.toString()
        o.rMtE = editTextRQHM10.text.toString()
        o.pMtE = editTextEquivalenteMT.text.toString()
        o.observaciones = editTextObservacion.text.toString()
        protocoloViewModel.validateProtocolo(o)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            P2Fragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}