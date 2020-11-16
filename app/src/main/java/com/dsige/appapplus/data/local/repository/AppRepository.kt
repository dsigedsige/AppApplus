package com.dsige.appapplus.data.local.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dsige.appapplus.data.local.model.*
import com.dsige.appapplus.helper.Mensaje
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call

interface AppRepository {

    fun getUsuario(): LiveData<Usuario>
    fun getUsuarioIdTask(): Int
    fun getUsuarioService(
        usuario: String, password: String, imei: String, version: String
    ): Observable<Usuario>

    fun insertUsuario(u: Usuario): Completable
    fun deleteUsuario(context: Context): Completable

    fun getSync(id: Int, version: String): Observable<Sync>
    fun saveSync(s: Sync): Completable

    fun getOtByTipoPaging(): LiveData<PagedList<Ot>>
    fun getOtByTipoPaging(id: Int): LiveData<PagedList<Ot>>
    fun getOtByTipoPaging(id: Int, s: String): LiveData<PagedList<Ot>>
    fun getOtByTipoPaging(s: String): LiveData<PagedList<Ot>>

    fun getOtById(id: Int): LiveData<Ot>
    fun getFormato(): LiveData<List<Formato>>
    fun getOtCabeceraByTipoPaging(id: Int): LiveData<PagedList<OtCabecera>>
    fun getOtCabeceraByTipoPaging(id: Int, e: Int): LiveData<PagedList<OtCabecera>>
    fun getOtDetalleByTipoPaging(): LiveData<PagedList<OtDetalle>>
    fun getOtDetalleByTipoPaging(e: Int): LiveData<PagedList<OtDetalle>>

    fun getOtCabeceraById(id: Int): LiveData<OtCabecera>
    fun getOtDetalleById(id: Int): LiveData<List<OtDetalle>>

    fun getFormById(id: Int): LiveData<OtDetalle>

    fun insertOrUpdateOtDetalle(d: OtDetalle): Completable

    fun getMaxIdOtDetalle(): LiveData<Int>
    fun getMaxIdOt(): LiveData<Int>

    fun generateCabecera(title: String, tipo: Int, codigo: String, id: Int, otId: Int): Completable

    fun insertOrUpdteOtEquipo(e: OtEquipo): Completable

    fun getEquipoByTipo(tipo: Int, formatoId: Int): LiveData<List<OtEquipo>>
    fun getEquipoById(id: Int): LiveData<OtEquipo>

    fun insertOrUpdteOtOtProtocolo(e: OtProtocolo): Completable
    fun getProtocoloByTipo(formatoId: Int, tipo: Int): LiveData<OtProtocolo>

    fun getHojaByTipo(tipo: Int, formatoId: Int): LiveData<List<Class<*>>>
    fun getHoja123ById(id: Int): LiveData<OtHoja123>
    fun insertOrUpdteOtHoja123(o: OtHoja123): Completable
    fun getHoja4ById(id: Int): LiveData<OtHoja4>
    fun insertOrUpdteOtHoja4(e: OtHoja4): Completable
    fun getHoja56ById(id: Int): LiveData<OtHoja56>
    fun insertOrUpdteOtHoja56(e: OtHoja56): Completable

    fun getOtCabeceraBySend(i: Int): Observable<List<OtCabecera>>
    fun updateRegistro(messages: Mensaje): Completable
    fun saveTrabajo(body: RequestBody): Observable<Mensaje>
    fun updateOt(body: RequestBody): Observable<Mensaje>
    fun updateEstadoOt(id: Int, estado: Int): Observable<Mensaje>
    fun getGrupoById(id: Int): LiveData<List<Grupo>>

    fun insertOrUpdateCabecera(o: OtCabecera): Completable
    fun getHojaById(id: Int): LiveData<OtCabecera>

    fun getHoja123ByItem(item: Int, formatoId: Int): LiveData<OtHoja123>
    fun getHoja567ByItem(item: Int, formatoId: Int): LiveData<OtHoja56>
    fun getHojaByItem(item: Int, formatoId: Int): LiveData<Class<*>>

    fun getEquipoDetalle(tipo: Int, formatoId: Int): LiveData<OtEquipo>

    fun findSed(sed: String): Observable<String>
    fun getEstados(): LiveData<List<EstadoTrabajo>>

    fun getPhotoById(id: Int): LiveData<List<OtPhoto>>
    fun insertOrUpdatePhoto(o: OtPhoto): Completable
    fun deletePhoto(o: OtPhoto): Completable

    fun getOtSend(i: Int): Observable<List<Ot>>
    fun getOtSendById(id: Int): Observable<Ot>
    fun updateOtById(m: Mensaje): Completable
    fun updateOtReasignacion(o: Ot): Completable
    fun changeEstado(otId: Int, estado: Int): Completable
    fun getCadistas(): LiveData<List<Cadista>>
    fun getPuestoTierra(): LiveData<List<PuestoTierra>>

    fun insertOrUpdateParteDiario(p: ParteDiario): Completable
    fun getParteDiarioByOt(id: Int): LiveData<ParteDiario>

    fun getSupervisor(): LiveData<List<Supervisor>>
    fun getOtSendParteDiario(): Observable<List<ParteDiario>>
    fun updateParteDiario(m: Mensaje): Completable
    fun sendParteDiario(body: RequestBody): Observable<Mensaje>

    fun saveGps(body: RequestBody): Call<Mensaje>
    fun saveMovil(body: RequestBody): Call<Mensaje>

    fun addOtParteDiario(o: List<Ot>): Completable

    fun getInspecciones(): LiveData<List<InspeccionPoste>>
    fun getInspecciones(e: Int): LiveData<List<InspeccionPoste>>
    fun getEstadoPostes(): LiveData<List<EstadoPoste>>
    fun getInspeccionById(id: Int): LiveData<InspeccionPoste>

    fun insertInspeccionPoste(p: InspeccionPoste): Completable

    fun getInspeccionConductorById(inspeccionId: Int): LiveData<InspeccionConductor>
    fun insertInspeccionConductor(p: InspeccionConductor): Completable

    fun getInspeccionCableById(inspeccionId: Int): LiveData<InspeccionCable>
    fun insertInspeccionCable(p: InspeccionCable): Completable

    fun getInspeccionEquipoById(inspeccionId: Int): LiveData<InspeccionEquipo>
    fun insertInspeccionEquipo(p: InspeccionEquipo): Completable

    fun getInspeccionesTask(): Observable<List<InspeccionPoste>>

    fun getInspeccionPhotoById(id: Int): LiveData<List<InspeccionPhoto>>

    fun deleteInspeccionPhoto(o: InspeccionPhoto, context: Context): Completable
    fun insertInspeccionPhoto(o: InspeccionPhoto): Completable
    fun getInspeccionesPhotoTask(): Observable<List<InspeccionPhoto>>
    fun sendInspeccionPhotos(body: RequestBody): Observable<String>
    fun sendInspecciones(body: RequestBody): Observable<Mensaje>
    fun updateInspeccion(m: Mensaje): Completable
}