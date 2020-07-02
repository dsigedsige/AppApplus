package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Supervisor {

    @PrimaryKey(autoGenerate = true)
    var supervisorId: Int = 0
    var nombre: String = ""
}