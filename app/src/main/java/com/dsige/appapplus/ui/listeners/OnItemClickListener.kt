package com.dsige.appapplus.ui.listeners

import android.view.View
import com.dsige.appapplus.data.local.model.*

interface OnItemClickListener {

    interface MenuListener {
        fun onItemClick(m: MenuPrincipal, view: View, position: Int)
    }

    interface FormatoListener {
        fun onItemClick(f: Formato, view: View, position: Int)
    }

    interface OTCabeceraListener {
        fun onItemClick(r: OtCabecera, view: View, position: Int)
    }

    interface OTEquipoListener {
        fun onItemClick(r: OtEquipo, view: View, position: Int)
    }

    interface OTListener {
        fun onItemClick(o: Ot, view: View, position: Int)
    }

    interface OTDetalleListener {
        fun onItemClick(d: OtDetalle, view: View, position: Int)
    }

    interface OTHojaListener {
        fun onItemClick(d: Class<*>, view: View, position: Int)
    }

}
