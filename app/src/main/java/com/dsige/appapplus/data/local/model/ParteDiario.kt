package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class ParteDiario {

    @PrimaryKey(autoGenerate = true)
    var parteDiarioId: Int = 0
    var fechaSalidaProgramada: String = ""
    var horaInicio: String = ""
    var horaFin: String = ""
    var sector: String = ""
    var proyectistaId: Int = 0
    var trabajoProgramadoId: Int = 0
    var supervisorId: Int = 0
    var distritoId: Int = 0
    var otId: Int = 0
    var nroSed: String = ""
    var fechaSalida: String = ""
    var cuadrillaId: Int = 0
    var placaId: Int = 0
    var observaciones: String = ""
    var estado: Int = 0
    var usuarioId: Int = 0

    // movil
    var turno: String = ""
    var nombreTrabajoProgramado: String = ""
    var active: Int = 0
    var identity: Int = 0
}