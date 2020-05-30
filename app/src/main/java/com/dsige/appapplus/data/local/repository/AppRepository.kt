package com.dsige.appapplus.data.local.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dsige.appapplus.data.local.model.*
import io.reactivex.Completable
import io.reactivex.Observable

interface AppRepository {

    fun getUsuario(): LiveData<Usuario>

    fun getUsuarioService(
        usuario: String,
        password: String,
        imei: String,
        version: String
    ): Observable<Usuario>

    fun insertUsuario(u: Usuario): Completable

    fun deleteUsuario(): Completable

    fun deleteTotal(): Completable

    fun getSync(id: Int, version: String): Observable<Sync>

    fun saveSync(s: Sync): Completable

    fun getOtByTipoPaging(): LiveData<PagedList<Ot>>

    fun getOtByTipoPaging(e: String): LiveData<PagedList<Ot>>

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

    fun getMaxIdOtDetalle() : LiveData<Int>

    fun getMaxIdOt(): LiveData<Int>

    fun generateCabecera(title: String, tipo: Int, codigo: String, id: Int,otId:Int) : Completable

    fun insertOrUpdteOtEquipo(e: OtEquipo):Completable

    fun getEquipoByTipo(tipo: Int, formatoId: Int): LiveData<List<OtEquipo>>

    fun getEquipoById(id: Int): LiveData<OtEquipo>

    fun insertOrUpdteOtOtProtocolo(e: OtProtocolo): Completable

    fun getProtocoloByTipo(formatoId: Int, tipo: Int): LiveData<OtProtocolo>
}