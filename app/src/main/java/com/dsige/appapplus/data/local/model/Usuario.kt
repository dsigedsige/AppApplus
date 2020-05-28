package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Usuario {
    @PrimaryKey(autoGenerate = true)
    var usuarioId: Int = 0
    var documento: String = ""
    var apellidos: String = ""
    var nombres: String = ""
    var email: String = ""
    var perfilId: Int = 0
    var estado: Int = 0
}