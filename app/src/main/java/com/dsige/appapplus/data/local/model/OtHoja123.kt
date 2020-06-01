package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtHoja123 {

    @PrimaryKey(autoGenerate = true)
    var hoja123Id: Int = 0
    var formatoId: Int = 0
    var item: Int = 0
    var nroCelda: String = ""
    var funcion: String = ""
    var cliente: String = ""
    var suminisrro: String = ""
    var equipo: String = ""
    var tipo_Fijo: String = ""
    var tipo_Extraib: String = ""
    var subtipo: String = ""
    var kardex: String = ""
    var nroFabrica: String = ""
    var marca: String = ""
    var modelo: String = ""
    var inom: String = ""
    var mando: String = ""
    var rele: String = ""
    var irele: String = ""
    var tipo: String = ""
}