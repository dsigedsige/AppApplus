package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtHoja56 {

    @PrimaryKey(autoGenerate = true)
    var hoja56Id: Int = 0
    var formatoId: Int = 0
    var item: Int = 0
    var tipoTablero: Int = 0
    var Aereo: Int = 0
    var Anivel: Int = 0
    var idtipo: Int = 0
    var base: String = ""
    var fusible: String = ""
    var seccion: String = ""
    var observacion: String = ""
}