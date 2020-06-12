package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
open class Ot {
    @PrimaryKey(autoGenerate = true)
    var otId: Int = 0
    var numeroOt: String = ""
    var nroOt: String = ""
    var descripcion: String = ""
    var nombre: String = ""
    var direccion: String = ""
    var distrito: String = ""
    var fechaLegal: String = ""
    var fechaAsignacion: String = ""
    var proyectistaId: Int = 0
    var fechaRecepcion: String = ""
    var estado: String = ""
    var diasVencimiento: Int = 0
    var estadoId : Int = 0

    @Ignore
    var otCabecera : List<OtCabecera> = ArrayList()
}