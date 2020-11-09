package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class InspeccionPoste {
    @PrimaryKey(autoGenerate = true)
    var inspeccionCampoId: Int = 0
    var inspeccionId: Int = 0
    var itemGeneral: Int = 0
    var codPoste: String = ""
    var distritoCod: String = ""
    var direccion: String = ""
    var nlote: String = ""
    var tipoCablePre: String = ""
    var cantCable: Int = 0
    var estadoId: Int = 0
    var nombreEstado: String = ""
    var latitud: String = ""
    var longitud: String = ""
}