package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtDetalle {

    @PrimaryKey(autoGenerate = true)
    var formatoDetalleId: Int = 0
    var formatoId: Int = 0
    var codigoSoporte: String = ""
    var alim: String = ""
    var armado: String = ""
    var tipoMaterialId: String = ""
    var nombreTipoMaterialId: String = ""
    var tamanio: String = ""
    var funcionId: String = ""
    var nombreFuncionId: String = ""
    var redSDS: String = ""
    var redAP: String = ""
    var redAmbas: String = ""
    var cNumeroId: Int = 0
    var seccCod: String = ""
    var seccCap: String = ""
    var seccFus: String = ""
    var tipoConductorId: String = ""
    var nombreTipoConductorId: String = ""
    var lvano: String = ""
    var conduSecc: String = ""
    var conduFases: String = ""

    var tipoAisladorId: String = ""
    var aislaMaterial: String = ""
    var aislaCantidad: String = ""
    var vientoViolin: String = ""
    var vientoSimple: String = ""
    var vientoCantidad: String = ""
    var pastoral: String = ""
    var observaciones: String = ""
    var codigoVia: String = ""
    var llave: String = ""
    var sistemas: String = ""
    var cajaDeriva: String = ""

    var retenidaV: String = ""
    var retenidaS: String = ""
    var pastotalC: String = ""
    var pastotalGF: String = ""
    var equipoTipo: String = ""
    var equipoModelo: String = ""
    var lampara: String = ""
    var direccion: String = ""

    var fecha:String = ""
}