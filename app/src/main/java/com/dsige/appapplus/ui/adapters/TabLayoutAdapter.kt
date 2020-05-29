package com.dsige.appapplus.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dsige.appapplus.ui.fragments.*

abstract class TabLayoutAdapter {

    class TabLayoutEquipo(fm: FragmentManager, private val numberOfTabs: Int, private val id: Int) :
        FragmentStatePagerAdapter(fm, numberOfTabs) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> EquipoFragment.newInstance(position+1, id,"Transformadores")
                1 -> EquipoFragment.newInstance(position+1, id,"Maniobra")
                2 -> EquipoFragment.newInstance(position+1, id,"Protección")
                3 -> EquipoFragment.newInstance(position+1, id,"Redes Areas")
                else -> Fragment()
            }
        }

        override fun getCount(): Int {
            return numberOfTabs
        }
    }


}