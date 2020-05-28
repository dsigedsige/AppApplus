package com.dsige.appapplus.data.local.repository

import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.data.local.AppDataBase
import com.dsige.appapplus.data.local.model.*
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class AppRepoImp(private val apiService: ApiService, private val dataBase: AppDataBase) :
    AppRepository {

    override fun getUsuario(): LiveData<Usuario> {
        return dataBase.usuarioDao().getUsuario()
    }

    override fun getUsuarioService(
        usuario: String, password: String, imei: String, version: String
    ): Observable<Usuario> {
        val u = Filtro(usuario, password, imei, version)
        val json = Gson().toJson(u)
        Log.i("TAG", json)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        return apiService.getLogin(body)
    }

    override fun insertUsuario(u: Usuario): Completable {
        return Completable.fromAction {
            dataBase.usuarioDao().insertUsuarioTask(u)
        }
    }

    override fun deleteUsuario(): Completable {
        return Completable.fromAction {
            dataBase.usuarioDao().deleteAll()
        }
    }

    override fun deleteTotal(): Completable {
        return Completable.fromAction {
            Util.deleteDirectory(
                File(Environment.getExternalStorageDirectory(), Util.Folder)
            )

        }
    }

    override fun getSync(id: Int, version: String): Observable<Sync> {
        return apiService.getSync(id, version)
    }

    override fun saveSync(s: Sync): Completable {
        return Completable.fromAction {
            val f: List<Formato>? = s.formatos
            if (f != null) {
                dataBase.formatoDao().insertFormatoListTask(f)
            }

            val c: List<Grupo>? = s.grupos
            if (c != null) {
                dataBase.grupoDao().insertGrupoListTask(c)
            }

            val e: List<Ot>? = s.ots
            if (e != null) {
                dataBase.otDao().insertOtListTask(e)
                for (ot in e) {
                    val otCabecera: List<OtCabecera>? = ot.otCabecera
                    if (otCabecera != null)
                        dataBase.otCabeceraDao().insertRegistroListTask(otCabecera)
                }
            }
        }
    }

    override fun getOtByTipoPaging(): LiveData<PagedList<Ot>> {
        return dataBase.otDao().getOtByTipoPaging().toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtByTipoPaging(e: String): LiveData<PagedList<Ot>> {
        return dataBase.otDao().getOtByTipoPaging(e).toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtById(id: Int): LiveData<Ot> {
        return dataBase.otDao().getOtById(id)
    }

    override fun getFormato(): LiveData<List<Formato>> {
        return dataBase.formatoDao().getFormato()
    }

    override fun getOtCabeceraByTipoPaging(id: Int): LiveData<PagedList<OtCabecera>> {
        return dataBase.otCabeceraDao().getOtCabeceraByTipoPaging(id).toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtCabeceraByTipoPaging(id: Int, e: Int): LiveData<PagedList<OtCabecera>> {
        return dataBase.otCabeceraDao().getOtCabeceraByTipoPaging(id, e).toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtDetalleByTipoPaging(): LiveData<PagedList<OtDetalle>> {
        return dataBase.otDetalleDao().getOtDetalleByTipoPaging().toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtDetalleByTipoPaging(e: Int): LiveData<PagedList<OtDetalle>> {
        return dataBase.otDetalleDao().getOtDetalleByTipoPaging(e).toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtCabeceraById(id: Int): LiveData<OtCabecera> {
        return dataBase.otCabeceraDao().getOtCabeceraById(id)
    }

    override fun getOtDetalleById(id: Int): LiveData<List<OtDetalle>> {
        return dataBase.otDetalleDao().getOtDetalleById(id)
    }

    override fun getFormById(id: Int): LiveData<OtDetalle> {
        return dataBase.otDetalleDao().getFormById(id)
    }

    override fun insertOrUpdateOtDetalle(d: OtDetalle): Completable {
        return Completable.fromAction {
            val detalle: OtDetalle? =
                dataBase.otDetalleDao().getOtDetalleVerificate(d.formatoDetalleId)
            if (detalle == null) {
                dataBase.otDetalleDao().insertRegistroDetalleTask(d)
            } else
                dataBase.otDetalleDao().updateRegistroDetalleTask(d)
        }
    }

    override fun getMaxIdOtDetalle(): LiveData<Int> {
        return dataBase.otDetalleDao().getMaxIdOtDetalle()
    }

    override fun getMaxIdOt(): LiveData<Int> {
        return dataBase.otCabeceraDao().getMaxIdOt()
    }

    override fun generateCabecera(
        title: String, tipo: Int, codigo: String, id: Int, otId: Int
    ): Completable {
        return Completable.fromAction {
            val c: OtCabecera? = dataBase.otCabeceraDao().getOtCabeceraByIdTask(id)
            if (c == null) {
                val ot = OtCabecera()
                ot.formatoId = id
                ot.tipoFormatoId = tipo
                ot.nombreTipoFormato = title
                ot.nroOt = codigo
                ot.otId = otId
                dataBase.otCabeceraDao().insertRegistroTask(ot)
            }
        }
    }
}