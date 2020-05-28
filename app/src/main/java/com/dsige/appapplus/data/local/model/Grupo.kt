package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Grupo {

    @PrimaryKey(autoGenerate = true)
    var grupoId: Int = 0
    var nombre: String = ""
    var detalleId: Int = 0
    var codigo: String = ""
    var descripcion: String = ""
    var vencimiento: String = ""
    var valor: String = ""
    var estado: Int = 0
}