package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class InspeccionCable {
    @PrimaryKey(autoGenerate = true)
    var inspeccionCableId: Int = 0
    var inspeccionCampoId: Int = 0
    var circuitoBT: Double = 0.0
    var condIzqTipo1: Int = 0
    var condIzqAltura1_BT: Double = 0.0
    var condIzqTipo2: Int = 0
    var condIzqAltura2_BT: Double = 0.0
    var cableBT: Double = 0.0
    var condDerTipo1: Int = 0
    var condDerAltura1_BT: Double = 0.0
    var condDerTipo2: Int = 0
    var condDerAltura2_BT: Double = 0.0
    var longbrazo: Double = 0.0
    var condAdeTipo1: Int = 0
    var condAdeAltura1_BT: Double = 0.0
    var condAdeTipo2: Int = 0
    var condAdeAltura2_BT: Double = 0.0
    var condAtrasTipo1: Int = 0
    var condAtrasAltura1_BT: Double = 0.0
    var condAtrasTipo2: Int = 0
    var condAtrasAltura2_BT: Double = 0.0
    var comentarioCableBT: String = ""
    var tipoCable1: String = ""
    var condIzqCant1: Double = 0.0
    var condIzqAltura1_Te: Double = 0.0
    var condIzqCant2: Double = 0.0
    var condIzqAltura2_Te: Double = 0.0
    var tipoCable2: String = ""
    var condDerCant1: Double = 0.0
    var condDerAltura1_Te: Double = 0.0
    var condDerCant2: Double = 0.0
    var condDerAltura2_Te: Double = 0.0
    var cableAdss: String = ""
    var condAdeCant1: Double = 0.0
    var condAdeAltura1_Te: Double = 0.0
    var condAdeCant2: Double = 0.0
    var condAdeAltura2_Te: Double = 0.0
    var cableCoaxial: String = ""
    var condAtrasCant1: Double = 0.0
    var condAtrasAltura1_Te: Double = 0.0
    var condAtrasCant2: Double = 0.0
    var condAtrasAltura2_Te: Double = 0.0
    var otrosCables: String = ""
    var longCant1: Double = 0.0
    var longAltura1_Te: Double = 0.0
    var longCant2: Double = 0.0
    var longAltura2_Te: Double = 0.0
    var comentarioTele: String = ""

    var identity : Int = 0

    //nombres de los id

  var  condIzqTipo1Nombre :String = ""
  var  condIzqTipo2Nombre :String = ""
  var  condDerTipo1Nombre :String = ""
  var  condDerTipo2Nombre :String = ""
  var  condAdeTipo1Nombre :String = ""
  var  condAdeTipo2Nombre :String = ""
  var  condAtrasTipo1Nombre :String = ""
  var  condAtrasTipo2Nombre :String = ""

}