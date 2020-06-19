package com.dsige.appapplus.data.local.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dsige.appapplus.data.local.model.*
import com.dsige.appapplus.helper.Mensaje
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.RequestBody

interface AppRepository {

    fun getUsuario(): LiveData<Usuario>

    fun getUsuarioService(
        usuario: String, password: String, imei: String, version: String
    ): Observable<Usuario>

    fun insertUsuario(u: Usuario): Completable

    fun deleteUsuario(): Completable

    fun deleteTotal(): Completable

    fun getSync(id: Int, version: String): Observable<Sync>

    fun saveSync(s: Sync): Completable

    fun getOtByTipoPaging(): LiveData<PagedList<Ot>>

    fun getOtByTipoPaging(id: Int): LiveData<PagedList<Ot>>

    fun getOtByTipoPaging(id: Int,s:String): LiveData<PagedList<Ot>>

    fun getOtByTipoPaging(s:String): LiveData<PagedList<Ot>>

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

    fun getGrupoById(id: Int): LiveData<List<Grupo>>

    fun insertOrUpdateCabecera(o: OtCabecera): Completable

    fun getHojaById(id: Int): LiveData<OtCabecera>

    fun getHoja123ByItem(item: Int, formatoId: Int): LiveData<OtHoja123>

    fun getHoja567ByItem(item: Int, formatoId: Int): LiveData<OtHoja56>

    fun getHojaByItem(item: Int, formatoId: Int): LiveData<Class<*>>

    fun getEquipoDetalle(tipo: Int, formatoId: Int): LiveData<OtEquipo>

    fun findSed(sed: String) : Observable<String>

    fun getEstados(): LiveData<List<Estado>>

    fun getPhotoById(id: Int): LiveData<List<OtPhoto>>

    fun insertOrUpdatePhoto(o: OtPhoto): Completable

    fun deletePhoto(o: OtPhoto): Completable

    fun getOtSend(i: Int): Observable<List<Ot>>

    fun getOtSendById(id: Int): Observable<Ot>

    fun updateOtById(m: Mensaje): Completable

    fun updateOtReasignacion(o: Ot): Completable

    fun changeEstado(otId: Int): Completable
}