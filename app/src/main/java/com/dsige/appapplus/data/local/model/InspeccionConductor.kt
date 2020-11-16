package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class InspeccionConductor {

    @PrimaryKey(autoGenerate = true)
    var inspeccionConductorId: Int = 0
    var inspeccionCampoId: Int = 0
    var id_ternaMT: Int = 0
    var id_nCond: Int = 0
    var id_TipoArmado: Int = 0
    var seccionIzq: Double = 0.0
    var seccionDer: Double = 0.0
    var seccionAdel: Double = 0.0
    var seccionAtras: Double = 0.0
    var vanoIzq: Double = 0.0
    var vanoDer: Double = 0.0
    var vanoAdel: Double = 0.0
    var vanoAtras: Double = 0.0
    var alturaIzq: Double = 0.0
    var alturaDer: Double = 0.0
    var alturaAdel: Double = 0.0
    var alturaAtras: Double = 0.0
    var distanciaIzq: Double = 0.0
    var distanciaDer: Double = 0.0
    var distanciaAdel: Double = 0.0
    var distanciaAtras: Double = 0.0
    var retIzq_1: Double = 0.0
    var retIzq_2: Double = 0.0
    var retIzq_3: Double = 0.0
    var retIzq_Estado: Int = 0
    var retDer_1: Double = 0.0
    var retDer_2: Double = 0.0
    var retDer_3: Double = 0.0
    var retDer_Estado: Int = 0
    var retAtras_1: Double = 0.0
    var retAtras_2: Double = 0.0
    var retAtras_3: Double = 0.0
    var retAtras_Estado: Int = 0
    var retAde_1: Double = 0.0
    var retAde_2: Double = 0.0
    var retAde_3: Double = 0.0
    var retAde_Estado: Int = 0
    var comentario: String = ""

    var identity: Int = 0

    //nombres de los id
    var id_ternaMTNombre: String = ""
    var id_nCondNombre: String = ""
    var id_TipoArmadoNombre: String = ""
    var retIzq_EstadoNombre: String = ""
    var retAtras_EstadoNombre: String = ""
    var retDer_EstadoNombre: String = ""
    var retAde_EstadoNombre: String = ""
}