package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.dsige.appapplus.R
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"

class HojaMainFragment : DaggerFragment() {


    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    lateinit var fabAdd : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hoja_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fabAdd = activity!!.findViewById(R.id.fabAdd)

        bindUI()
    }

    private fun bindUI(){
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, tipo: Int, id: Int, codigo: String, otId: Int) =
            HojaMainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, title)
                    putInt(ARG_PARAM2, tipo)
                    putInt(ARG_PARAM3, id)
                    putString(ARG_PARAM4, codigo)
                    putInt(ARG_PARAM5, otId)
                }
            }
    }
}