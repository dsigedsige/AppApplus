package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
open class OtCabecera {

    @PrimaryKey(autoGenerate = true)
    var formatoId: Int = 0
    var tipoFormatoId: Int = 0
    var nombreTipoFormato: String = ""
    var seccion: String = ""
    var otId: Int = 0
    var nroOt: String = ""
    var nombrecliente: String = ""
    var alimentador: String = ""
    var modulo: String = ""
    var sed: String = ""
    var ubicacion: String = ""
    var distrito: String = ""
    var ubicacionSed: String = ""
    var coordenadaX: String = ""
    var coordenadaY: String = ""
    var fechaRegistro: String = ""
    var estadoId: Int = 0
    var nombreEstado: String = ""

    @Ignore
    var details: List<OtDetalle> = ArrayList()
}