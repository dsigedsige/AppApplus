package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class OtProtocolo {

    @PrimaryKey(autoGenerate = true)
    var protocoloId: Int = 0
    var formatoId: Int = 0
    var tipoProtocoloId: Int = 0 // tipo de cada pestaña
    var tipoTerreno: String = ""
    var estadoTerreno: String = ""
    var resistenciaSeca: String = ""
    var resistenciaHumeda: String = ""
    var fechaSeca: String = ""
    var aSeca1: String = ""
    var aSeca2: String = ""
    var aSeca3: String = ""
    var fechaHumeda: String = ""
    var aHumeda1: String = ""
    var aHumeda2: String = ""
    var aHumeda3: String = ""


    var rBt1: String = ""
    var pBt1: String = ""
    var rBt2: String = ""
    var pBt2: String = ""
    var rBt3: String = ""
    var pBt3: String = ""
    var rBt4: String = ""
    var pBt4: String = ""
    var rBtE: String = ""
    var pBtE: String = ""
    var rMt1: String = ""
    var pMt1: String = ""
    var rMt2: String = ""
    var pMt2: String = ""
    var rMt3: String = ""
    var pMt3: String = ""
    var rMt4: String = ""
    var pMt4: String = ""
    var rMtE: String = ""
    var pMtE: String = ""


    var observaciones: String = ""
    var proyecto: String = ""
    var tipoSistema: String = ""
    var grupoResis: String = ""
    var nivelResis: String = ""
    var btNpozos: String = ""
    var btPozoTratado: String = ""
    var btNdosis: String = ""
    var btCompuesto: String = ""
    var mtNpozos: String = ""
    var mtPozoTratado: String = ""
    var mtNdosis: String = ""
    var mtCompuesto: String = ""
}