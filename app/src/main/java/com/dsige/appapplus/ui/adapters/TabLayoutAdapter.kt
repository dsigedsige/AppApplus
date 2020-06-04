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
                0 -> EquipoFragment.newInstance(position + 1, id, "Transformadores")
                1 -> EquipoFragment.newInstance(position + 1, id, "Maniobra")
                2 -> EquipoFragment.newInstance(position + 1, id, "Protección")
                3 -> EquipoFragment.newInstance(position + 1, id, "Redes Areas")
                else -> Fragment()
            }
        }

        override fun getCount(): Int {
            return numberOfTabs
        }
    }


    class TabLayoutHoja(fm: FragmentManager, private val numberOfTabs: Int, private val id: Int) :
        FragmentStatePagerAdapter(fm, numberOfTabs) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> HojaFragment.newInstance(id, position + 1)
                1 -> HojaFragment.newInstance(id, position + 1)
                2 -> HojaFragment.newInstance(id, position + 1)
                3 -> HojaFragment.newInstance(id, position + 1)
                4 -> HojaFragment.newInstance(id, position + 1)
                5 -> HojaFragment.newInstance(id, position + 1)
                6 -> HojaFragment.newInstance(id, position + 1)
                7 -> HojaFragment.newInstance(id, position + 1)
                else -> Fragment()
            }
        }

        override fun getCount(): Int {
            return numberOfTabs
        }
    }

    class TabLayoutProtocolo(
        fm: FragmentManager, private val numberOfTabs: Int, private val id: Int
    ) :
        FragmentStatePagerAdapter(fm, numberOfTabs) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> P1Fragment.newInstance(id, 1)
                1 -> P2Fragment.newInstance(id, 2)
                2 -> P3Fragment.newInstance(id, 3)
                3 -> P4Fragment.newInstance(id, 4)
                else -> Fragment()
            }
        }

        override fun getCount(): Int {
            return numberOfTabs
        }
    }
}