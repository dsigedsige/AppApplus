package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class InspeccionEquipo {
    @PrimaryKey(autoGenerate = true)
    var inspeccionEquipoId: Int = 0
    var inspeccionCampoId: Int = 0
    var electrico1: String = ""
    var cantidad1: Double = 0.0
    var telecomunica1: String = ""
    var telecantidad1: Double = 0.0
    var electrico2: String = ""
    var cantidad2: Double = 0.0
    var telecomunica2: String = ""
    var telecantidad2: Double = 0.0
    var electrico3: String = ""
    var cantidad3: Double = 0.0
    var telecomunica3: String = ""
    var telecantidad3: Double = 0.0
    var electrico4: String = ""
    var cantidad4: Double = 0.0
    var telecomunica4: String = ""
    var telecantidad4: Double = 0.0
    var comentario: String = ""

    var identity: Int = 0
}