package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Sed {
    @PrimaryKey
    var codigo: String = ""
}