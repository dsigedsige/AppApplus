package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Formato {

    @PrimaryKey(autoGenerate = true)
    var formatoId: Int = 0
    var nombre: String = ""
    var estado: String = ""
}