package com.dsige.appapplus.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
open class InspeccionPoste {
    @PrimaryKey(autoGenerate = true)
    var inspeccionCampoId: Int = 0
    var inspeccionId: Int = 0
    var itemGeneral: Int = 0
    var codPoste: String = ""
    var distritoCod: String = ""
    var direccion: String = ""
    var nlote: String = ""
    var tipoCablePre: String = ""
    var cantCable: Int = 0
    var estadoId: Int = 0
    var nombreEstado: String = ""
    var latitud: String = ""
    var longitud: String = ""

    // nuevo para los formularios
    var utmX: String = ""
    var utmY: String = ""
    var nroPoste: String = ""
    var id_MaterialSoporte: Int = 0
    var id_TipoPoste: Int = 0
    var id_CargaTrabajo: Int = 0
    var id_TipoArmado: Int = 0
    var alturaPoste: Double = 0.0
    var alturaLibrePoste: Double = 0.0
    var id_estadoMensula: Int = 0
    var id_NivelCriticidad: Int = 0
    var comentariosGenerales: String = ""
    var vanoMT_AngIzq: Double = 0.0
    var vanoMT_AngDer: Double = 0.0
    var vanoMT_AngAde: Double = 0.0
    var vanoMT_AngAtra: Double = 0.0
    var vanoMT_VanIzq: Double = 0.0
    var vanoMT_VanDer: Double = 0.0
    var vanoMT_VanAde: Double = 0.0
    var vanoMT_VanAtra: Double = 0.0
    var comentariosVanoMt: String = ""
    var vanoBT_AngIzq: Double = 0.0
    var vanoBT_AngDer: Double = 0.0
    var vanoBT_AngAde: Double = 0.0
    var vanoBT_AngAtra: Double = 0.0
    var vanoBT_VanIzq: Double = 0.0
    var vanoBT_VanDer: Double = 0.0
    var vanoBT_VanAde: Double = 0.0
    var vanoBT_VanAtra: Double = 0.0
    var AngRetenidaBt: Double = 0.0
    var comentariosVanoBt: String = ""
    var id_reteBT_tipoR1: Int = 0
    var reteBT_AlturaR1: Double = 0.0
    var reteBT_DirR1: String = ""
    var id_reteBT_Estado1: Int = 0
    var id_reteBT_tipoR2: Int = 0
    var reteBT_AlturaR2: Double = 0.0
    var reteBT_DirR2: String = ""
    var id_reteBT_Estado2: Int = 0
    var comentarios_ReteBT: String = ""
    var infoAdd_RolloReserva: String = ""
    var infoAdd_Fotos: String = ""
    var id_infoAdd_Consecion: Int = 0
    var infoAdd_Niveltension: String = ""
    var infoCnew_CableNuevo: String = ""
    var infoCnew_vanoIzq: Double = 0.0
    var infoCnew_vanoDer: Double = 0.0
    var infoCnew_vanoAde: Double = 0.0
    var infoCnew_vanoAtra: Double = 0.0
    var infoCnew_alturaInstala: Double = 0.0
    var obsC_Cumplimiento: Int = 0
    var obsC_PosteInclinado: Int = 0
    var obsC_PosteSubida: Int = 0
    var obsC_PosteSaturado: Int = 0
    var comentarios_obsC: String = ""
    var resFac_Factible: Int = 0
    var id_resFac_ObsPrincipal: Int = 0
    var Obs_generales: String = ""

    var usuarioId: Int = 0


    //movil
    var active: Int = 0 //

    //formulario principal
    var id_MaterialSoporteNombre: String = ""
    var id_TipoPosteNombre: String = ""
    var id_CargaTrabajoNombre: String = ""
    var id_TipoArmadoNombre: String = ""
    var id_estadoMensulaNombre: String = ""
    var id_NivelCriticidadNombre: String = ""

    //formulario 6
    var id_reteBT_tipoR1Nombre: String = ""
    var id_reteBT_Estado1Nombre: String = ""
    var id_reteBT_tipoR2Nombre: String = ""
    var id_reteBT_Estado2Nombre: String = ""

    //formulario 8
    var id_infoAdd_ConsecionNombre: String = ""

    //formulario 9
    var obsC_CumplimientoNombre: String = ""
    var obsC_PosteInclinadoNombre: String = ""
    var obsC_PosteSubidaNombre: String = ""
    var obsC_PosteSaturadoNombre: String = ""
    var resFac_FactibleNombre: String = ""
    var id_resFac_ObsPrincipalNombre: String = ""

    @Ignore
    var photos: List<InspeccionPhoto>? = null

    @Ignore
    var cable: InspeccionCable? = null

    @Ignore
    var conductor: InspeccionConductor? = null

    @Ignore
    var equipo: InspeccionEquipo? = null
}