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
    var estadoId: Int = 0

    var active: Int = 0 // 1 -> para enviar // 2 -> enviado
    var comentario: String = ""
    var motivoId: Int = 0
    var nombreMotivoId: String = ""
    var coordinadorId: Int = 0
    var nombreCoordinador: String = ""

    var supervisorId: Int = 0
    var nombreSupervisor: String = ""

    var estadoParteDiario: Int = 0 // 1 -> guardado 2 -> enviado
    var checkParteDiario: Int = 0 // 1-> activado  0 -> desactivado

    @Ignore
    var otCabecera: List<OtCabecera> = ArrayList()
//
//    @Ignore
//    var parteDiario: ParteDiario? = null

}