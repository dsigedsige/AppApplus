package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Cadista {

    @PrimaryKey
    var cadistaId: Int = 0
    var nombre: String = ""
    var pendientes: Int = 0
}