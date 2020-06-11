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
import com.dsige.appapplus.helper.Mensaje
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
                ot.active = 1
                dataBase.otCabeceraDao().insertRegistroTask(ot)
            }
        }
    }

    override fun insertOrUpdteOtEquipo(e: OtEquipo): Completable {
        return Completable.fromAction {
            if (e.equipoId == 0) {
                dataBase.otEquipoDao().insertRegistroDetalleTask(e)
            } else {
                dataBase.otEquipoDao().updateRegistroDetalleTask(e)
            }
        }
    }

    override fun getEquipoByTipo(tipo: Int, formatoId: Int): LiveData<List<OtEquipo>> {
        return dataBase.otEquipoDao().getEquipoByTipo(tipo, formatoId)
    }

    override fun getEquipoById(id: Int): LiveData<OtEquipo> {
        return dataBase.otEquipoDao().getEquipoById(id)
    }

    override fun insertOrUpdteOtOtProtocolo(e: OtProtocolo): Completable {
        return Completable.fromAction {
            if (e.protocoloId == 0) {
                dataBase.otProtocoloDao().insertProtocoloTask(e)
            } else {
                dataBase.otProtocoloDao().updateProtocoloTask(e)
            }
        }
    }

    override fun getProtocoloByTipo(formatoId: Int, tipo: Int): LiveData<OtProtocolo> {
        return dataBase.otProtocoloDao().getProtocoloByTipo(formatoId, tipo)
    }

    override fun getHojaByTipo(tipo: Int, formatoId: Int): LiveData<List<Class<*>>> {
        return when (tipo) {
            1, 2, 3 -> dataBase.otHoja123Dao().getHoja123ByTipo(formatoId, tipo)
            4 -> dataBase.otHoja4Dao().getHoja4ByTipo(formatoId, tipo)
            else -> dataBase.otHoja56Dao().getHoja56ByTipo(formatoId, tipo)
        } as LiveData<List<Class<*>>>
    }

    override fun getHoja123ById(id: Int): LiveData<OtHoja123> {
        return dataBase.otHoja123Dao().getHoja123ById(id)
    }

    override fun insertOrUpdteOtHoja123(o: OtHoja123): Completable {
        return Completable.fromAction {
            if (o.hoja123Id == 0)
                dataBase.otHoja123Dao().insertHoja123Task(o)
            else
                dataBase.otHoja123Dao().updateHoja123Task(o)
        }
    }

    override fun getHoja4ById(id: Int): LiveData<OtHoja4> {
        return dataBase.otHoja4Dao().getHoja4ById(id)
    }

    override fun insertOrUpdteOtHoja4(e: OtHoja4): Completable {
        return Completable.fromAction {
            if (e.hoja4Id == 0)
                dataBase.otHoja4Dao().insertHoja4Task(e)
            else
                dataBase.otHoja4Dao().updateHoja4Task(e)
        }
    }

    override fun getHoja56ById(id: Int): LiveData<OtHoja56> {
        return dataBase.otHoja56Dao().getHoja56ById(id)
    }

    override fun insertOrUpdteOtHoja56(e: OtHoja56): Completable {
        return Completable.fromAction {
            if (e.hoja56Id == 0)
                dataBase.otHoja56Dao().insertHoja56Task(e)
            else
                dataBase.otHoja56Dao().updateHoja56Task(e)
        }
    }

    override fun getOtCabeceraBySend(i: Int): Observable<List<OtCabecera>> {
        return Observable.create { e ->

            val ots: ArrayList<OtCabecera> = ArrayList()
            val ot: List<OtCabecera> = dataBase.otCabeceraDao().getOtCabeceraActive(i)

            if (ot.isEmpty()){
                e.onError(Throwable("Usted no tiene pendientes por enviar"))
                e.onComplete()
                return@create
            }

            for (o in ot) {
                o.details = dataBase.otDetalleDao().getDetalleFkId(o.formatoId)
                o.equipos = dataBase.otEquipoDao().getEquipoFkId(o.formatoId)
                o.hojas123 = dataBase.otHoja123Dao().getHoja123FkId(o.formatoId)
                o.hojas4 = dataBase.otHoja4Dao().getHoja4FkId(o.formatoId)
                o.hojas567 = dataBase.otHoja56Dao().getHoja56FkId(o.formatoId)
                o.protocolos = dataBase.otProtocoloDao().getProtocoloFkId(o.formatoId)
                ots.add(o)
            }

            e.onNext(ots)
            e.onComplete()
        }
    }

    override fun updateRegistro(messages: Mensaje): Completable {
        return Completable.fromAction {
            dataBase.otCabeceraDao().updateCabecera(messages.codigoBase, messages.codigoRetorno)
        }
    }

    override fun saveTrabajo(body: RequestBody): Observable<Mensaje> {
        return apiService.saveTrabajo(body)
    }

    override fun getGrupoById(id: Int): LiveData<List<Grupo>> {
        return dataBase.grupoDao().getGrupoById(id)
    }

    override fun insertOrUpdateCabecera(o: OtCabecera): Completable {
        return Completable.fromAction{
            val c: OtCabecera? = dataBase.otCabeceraDao().getOtCabeceraByIdTask(o.formatoId)
            if (c == null) {
                dataBase.otCabeceraDao().insertRegistroTask(o)
            }else
                dataBase.otCabeceraDao().updateRegistroTask(o)
        }
    }

    override fun getHojaById(id: Int): LiveData<OtCabecera> {
        return dataBase.otCabeceraDao().getRegistroById(id)
    }

    override fun getHoja123ByItem(item: Int, formatoId: Int): LiveData<OtHoja123> {
       return dataBase.otHoja123Dao().getHoja123ByItem(formatoId, item)
    }

    override fun getHoja567ByItem(item: Int, formatoId: Int): LiveData<OtHoja56> {
      return dataBase.otHoja56Dao().getHoja56ByItem(formatoId, item)
    }

    override fun getHojaByItem(item: Int, formatoId: Int): LiveData<Class<*>> {
        return when (item) {
            3 -> dataBase.otHoja123Dao().getHoja123ByItem(formatoId, item)
            else -> dataBase.otHoja56Dao().getHoja56ByItem(formatoId, item)
        } as LiveData<Class<*>>
    }

    override fun getEquipoDetalle(tipo: Int, formatoId: Int): LiveData<OtEquipo> {
        return dataBase.otEquipoDao().getEquipoDetalle(tipo,formatoId)
    }
}