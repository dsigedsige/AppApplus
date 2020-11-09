package com.dsige.appapplus.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dsige.appapplus.R
import dagger.android.support.DaggerFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Poste2Fragment : DaggerFragment() {
    private var inspeccionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            inspeccionId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poste_2, container, false)
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