package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtPhoto {
    @PrimaryKey(autoGenerate = true)
    var formatoFotoId: Int = 0
    var formatoId: Int = 0
    var fotoUrl: String = ""
    var observacion: String = ""
    var estado: Int = 0
    var usuarioId: Int = 0
    var fecha: String = ""
}