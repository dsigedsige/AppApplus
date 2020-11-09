package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class EstadoTrabajo {
    @PrimaryKey(autoGenerate = true)
    var estadoId: Int = 0
    var abreviatura: String = ""
    var descripcion: String = ""
    var tipoProceso: String = ""
    var descripcionTP: String = ""
    var moduloId: Int = 0
    var orden: Int = 0
    var backColor: String = ""
    var foreColor: String = ""
    var activo: Int = 0
}