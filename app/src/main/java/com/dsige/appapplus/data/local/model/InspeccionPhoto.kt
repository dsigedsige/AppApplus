package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class InspeccionPhoto {
    @PrimaryKey(autoGenerate = true)
    var inspeccionPhotoId: Int = 0
    var inspeccionCampoId: Int = 0
    var fotoUrl: String = ""
    var estado: Int = 0
    var usuarioId: Int = 0
    var fecha: String = ""
    var active : Int = 0
}