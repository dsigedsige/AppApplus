package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
open class OtCabecera {

    @PrimaryKey(autoGenerate = true)
    var formatoId: Int = 0
    var tipoFormatoId: Int = 0
    var nombreTipoFormato: String = ""
    var seccion: String = ""
    var seccionId: Int = 1
    var otId: Int = 0
    var nroOt: String = ""
    var nombrecliente: String = ""
    var alimentador: String = ""
    var modulo: String = ""
    var sed: String = ""
    var ubicacion: String = ""
    var distrito: String = ""
    var ubicacionSed: String = ""
    var nombreUbicacionSed: String = ""
    var coordenadaX: String = ""
    var coordenadaY: String = ""
    var fechaRegistro: String = ""
    var estadoId: Int = 0
    var nombreEstado: String = ""

    var convencional: String = ""
    var compacta: String = ""
    var aerea: String = ""
    var pmi: String = ""
    var aNivel: String = ""
    var pedestal: String = ""
    var monoposte: String = ""
    var reCloser: String = ""
    var subTerranea: String = ""
    var boveda: String = ""
    var biposte: String = ""
    var sbc: String = ""
    var soporte: String = ""
    var setProtocolo: String = ""
    var cuadrilla: String = ""
    var lamina: String = ""
    var letra: String = ""


    var active: Int = 0 // 1-> por enviar 0 -> enviado
    var identity: Int = 0
    var usuario: Int = 0
    var cadistaId: Int = 0
    var nombreCadista: String = ""
    var dibujar: String = ""

    var supervisorId: Int = 0
    var nombreSupervisor: String = ""

    @Ignore
    var details: List<OtDetalle> = ArrayList()

    @Ignore
    var equipos: List<OtEquipo> = ArrayList()

    @Ignore
    var hojas123: List<OtHoja123> = ArrayList()

    @Ignore
    var hojas4: List<OtHoja4> = ArrayList()

    @Ignore
    var hojas567: List<OtHoja56> = ArrayList()

    @Ignore
    var protocolos: List<OtProtocolo> = ArrayList()

    @Ignore
    var photos: List<OtPhoto> = ArrayList()

}