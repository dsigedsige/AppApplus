package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtEquipo {

    @PrimaryKey(autoGenerate = true)
    var otEquipoId: Int = 0
    var formatoId: Int = 0
    var campo1: String = ""
    var campo2: String = ""
    var campo3: String = ""
    var campo4: String = ""
    var campo5: String = ""
    var campo6: String = ""
    var campo7: String = ""
    var campo8: String = ""
    var campo9: String = ""
    var campo10: String = ""
    var estado: Int = 0
}