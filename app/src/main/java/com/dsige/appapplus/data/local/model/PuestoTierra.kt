package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class PuestoTierra {

    @PrimaryKey
    var proyectadoId: Int = 0
    var grupo: String = ""
    var resistividad: String = ""
    var pozo: String = ""
    var dosis: String = ""
    var tratado: String = ""
    var estado: Int = 0
}