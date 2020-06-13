package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtEquipo {

    @PrimaryKey(autoGenerate = true)
    var equipoId: Int = 0
    var formatoId: Int = 0
    var tipoEquipo: Int = 0
    var nroKardex: String = ""
    var nroFabrica: String = ""
    var sedUbicacion: String = ""
    var celdaUbicacion: String = ""
    var potenciaKVA: String = ""
    var anio: String = ""
    var marca: String = ""
    var tipo: String = ""
    var destino: String = ""
    var observacion: String = ""
    var funcionCelda: String = ""
    var enlace: String = ""
    var equipo: String = ""
    var nroPrc: String = ""
    var soporte: String = ""

    var fecha:String = ""
}