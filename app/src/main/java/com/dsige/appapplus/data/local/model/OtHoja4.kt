package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtHoja4 {

    @PrimaryKey(autoGenerate = true)
    var hoja4Id: Int = 0
    var formatoId: Int = 0
    var item: Int = 0 // tipo de formato
    var nroTrafo: String = ""
    var ubicacion: String = ""
    var nroFabrica: String = ""
    var potInst: String = ""
    var anio: String = ""
    var marca: String = ""
    var tipo: String = ""
    var kardex: String = ""
    var posicion: String = ""
    var relTransf: String = ""
    var potenciaCC: String = ""
    var nroFases: String = ""
    var cableC1: String = ""
    var cableC2: String = ""
    var cableC3: String = ""
    var cableC4: String = ""
    var cableC5: String = ""
    var disMarca: String = ""
    var disKardex: String = ""
    var disSerie: String = ""
    var disIA: String = ""
}