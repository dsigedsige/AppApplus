package com.dsige.appapplus.helper

open class Mensaje {
    var codigo: Int = 0
    var codigoBase: Int = 0
    var codigoRetorno: Int = 0
    var mensaje: String = ""
    var codigoRetornoCable: Int = 0
    var codigoRetornoEquipo: Int = 0
    var codigoRetornoConductor: Int = 0

    var detalle: List<MensajeDetalle>? = null

    constructor(codigo: Int, mensaje: String) {
        this.codigo = codigo
        this.mensaje = mensaje
    }
}