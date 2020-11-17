package com.dsige.appapplus.data.local.repository

import android.content.Context
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
import retrofit2.Call
import java.io.File

class AppRepoImp(private val apiService: ApiService, private val dataBase: AppDataBase) :
    AppRepository {

    override fun getUsuario(): LiveData<Usuario> {
        return dataBase.usuarioDao().getUsuario()
    }

    override fun getUsuarioIdTask(): Int {
        return dataBase.usuarioDao().getUsuarioIdTask()
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

    override fun deleteUsuario(context: Context): Completable {
        return Completable.fromAction {
            dataBase.estadoTrabajoDao().deleteAll()
            dataBase.estadoPosteDao().deleteAll()
            dataBase.formatoDao().deleteAll()
            dataBase.grupoDao().deleteAll()
            dataBase.otCabeceraDao().deleteAll()
            dataBase.otDao().deleteAll()
            dataBase.otDetalleDao().deleteAll()
            dataBase.otEquipoDao().deleteAll()
            dataBase.otHoja123Dao().deleteAll()
            dataBase.otHoja4Dao().deleteAll()
            dataBase.otHoja56Dao().deleteAll()
            dataBase.otPhotoDao().deleteAll()
            dataBase.otProtocoloDao().deleteAll()
            dataBase.sedDao().deleteAll()
            dataBase.usuarioDao().deleteAll()

            dataBase.puestoTierraDao().deleteAll()
            dataBase.parteDiarioDao().deleteAll()
            dataBase.supervisorDao().deleteAll()
            dataBase.cadistaDao().deleteAll()

            dataBase.inspeccionPosteDao().deleteAll()
            dataBase.inspeccionEquipoDao().deleteAll()
            dataBase.inspeccionConductorDao().deleteAll()
            dataBase.inspeccionCableDao().deleteAll()
            dataBase.inspeccionPhotoDao().deleteAll()
            Util.deleteDirectory(Util.getFolder(context))
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

            val l: List<Sed>? = s.seds
            if (l != null) {
                dataBase.sedDao().insertSedListTask(l)
            }

            val es: List<EstadoTrabajo>? = s.estadoTrabajo
            if (es != null) {
                dataBase.estadoTrabajoDao().insertEstadoListTask(es)
            }
            val es2: List<EstadoPoste>? = s.estadoPoste
            if (es2 != null) {
                dataBase.estadoPosteDao().insertEstadoListTask(es2)
            }

            val ca: List<Cadista>? = s.cadistas
            if (ca != null) {
                dataBase.cadistaDao().insertCadistaListTask(ca)
            }

            val t: List<PuestoTierra>? = s.puestos
            if (t != null) {
                dataBase.puestoTierraDao().insertPuestoTierraListTask(t)
            }

            val su: List<Supervisor>? = s.supervisores
            if (su != null) {
                dataBase.supervisorDao().insertSupervisorListTask(su)
            }

            val a1: List<InspeccionPoste>? = s.otsPostes
            if (a1 != null) {
                dataBase.inspeccionPosteDao().insertInspeccionPosteListTask(a1)
            }
        }
    }

    override fun getOtByTipoPaging(): LiveData<PagedList<Ot>> {
        return dataBase.otDao().getOtByTipoPaging().toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtByTipoPaging(id: Int): LiveData<PagedList<Ot>> {
        return dataBase.otDao().getOtByTipoPaging(id).toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtByTipoPaging(id: Int, s: String): LiveData<PagedList<Ot>> {
        return dataBase.otDao().getOtByTipoPaging(id, s).toLiveData(
            Config(pageSize = 20, enablePlaceholders = true)
        )
    }

    override fun getOtByTipoPaging(s: String): LiveData<PagedList<Ot>> {
        return dataBase.otDao().getOtByTipoPaging(s).toLiveData(
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

            if (ot.isEmpty()) {
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
                o.photos = dataBase.otPhotoDao().getPhotoFkId(o.formatoId)
                ots.add(o)
            }

            e.onNext(ots)
            e.onComplete()
        }
    }

    override fun updateRegistro(messages: Mensaje): Completable {
        return Completable.fromAction {
            val a: OtCabecera? =
                dataBase.otCabeceraDao().getOtCabeceraPerfil(messages.codigoBase, 11)
            if (a != null) {
                dataBase.otDao().changeEstado(a.otId, 3)
            }
            dataBase.otCabeceraDao().updateCabecera(messages.codigoBase, messages.codigoRetorno)
        }
    }

    override fun saveTrabajo(body: RequestBody): Observable<Mensaje> {
        return apiService.saveTrabajo(body)
    }

    override fun updateOt(body: RequestBody): Observable<Mensaje> {
        return apiService.updateOt(body)
    }

    override fun updateEstadoOt(id: Int, estado: Int): Observable<Mensaje> {
        return apiService.updateEstadoOt(id, estado)
    }

    override fun getGrupoById(id: Int): LiveData<List<Grupo>> {
        return dataBase.grupoDao().getGrupoById(id)
    }

    override fun insertOrUpdateCabecera(o: OtCabecera): Completable {
        return Completable.fromAction {
            val c: OtCabecera? = dataBase.otCabeceraDao().getOtCabeceraByIdTask(o.formatoId)
            if (c == null) {
                dataBase.otCabeceraDao().insertRegistroTask(o)
            } else
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
        return dataBase.otEquipoDao().getEquipoDetalle(tipo, formatoId)
    }

    override fun findSed(sed: String): Observable<String> {
        return Observable.create { e ->
            val s: Sed? = dataBase.sedDao().getFindSed(sed)
            if (s == null) {
                e.onError(Throwable("Sed no encontrado"))
                e.onComplete()
                return@create
            }
            e.onNext(s.alimentador)
            e.onComplete()
        }
    }

    override fun getEstados(): LiveData<List<EstadoTrabajo>> {
        return dataBase.estadoTrabajoDao().getEstados()
    }

    override fun getPhotoById(id: Int): LiveData<List<OtPhoto>> {
        return dataBase.otPhotoDao().getPhotoById(id)
    }

    override fun insertOrUpdatePhoto(o: OtPhoto): Completable {
        return Completable.fromAction {
            if (o.formatoFotoId == 0) {
                dataBase.otPhotoDao().insertOtPhotoTask(o)
            } else {
                dataBase.otPhotoDao().updateOtPhotoTask(o)
            }
            val a = dataBase.otCabeceraDao().getOtCabeceraByIdTask(o.formatoId)
            if (a.estadoPerfil == 11) {
                dataBase.otCabeceraDao().updateActiveSendPerfil(a.formatoId)
            }
        }
    }

    override fun deletePhoto(o: OtPhoto): Completable {
        return Completable.fromAction {
            dataBase.otPhotoDao().deleteOtPhotoTask(o)
        }
    }

    override fun getOtSend(i: Int): Observable<List<Ot>> {
        return Observable.create { e ->
            val ot = dataBase.otDao().getOtReasignacion(i)
            if (ot.isEmpty()) {
                e.onError(Throwable("Usted no tiene pendientes por enviar"))
                e.onComplete()
                return@create
            }
            e.onNext(ot)
            e.onComplete()
        }
    }

    override fun getOtSendById(id: Int): Observable<Ot> {
        return Observable.create { e ->
            e.onNext(dataBase.otDao().getOtSendById(id))
            e.onComplete()
        }
    }

    override fun updateOtById(m: Mensaje): Completable {
        return Completable.fromAction {
            dataBase.otDao().updateOtById(m.codigoBase)
        }
    }

    override fun updateOtReasignacion(o: Ot): Completable {
        return Completable.fromAction {
            dataBase.otDao().updateOtTask(o)
        }
    }

    // 8
    override fun changeEstado(otId: Int, estado: Int): Completable {
        return Completable.fromAction {
            dataBase.otDao().changeEstado(otId, estado)
        }
    }

    override fun getCadistas(): LiveData<List<Cadista>> {
        return dataBase.cadistaDao().getCadistas()
    }

    override fun getPuestoTierra(): LiveData<List<PuestoTierra>> {
        return dataBase.puestoTierraDao().getPuestoTierras()
    }

    override fun insertOrUpdateParteDiario(p: ParteDiario): Completable {
        return Completable.fromAction {

            if (p.otId == 0) {
                val ots = dataBase.otDao().getCheckOtParteDiario()
                for (o: Ot in ots) {
                    dataBase.otDao().updateEstadoOt(o.otId)
                    p.otId = o.otId
                    dataBase.parteDiarioDao().insertParteDiarioTask(p)
                }
                dataBase.otDao().disabledOtParteDiario()
            } else {
                if (p.parteDiarioId == 0)
                    dataBase.parteDiarioDao().insertParteDiarioTask(p)
                else
                    dataBase.parteDiarioDao().updateParteDiarioTask(p)
            }
        }
    }

    override fun getParteDiarioByOt(id: Int): LiveData<ParteDiario> {
        return dataBase.parteDiarioDao().getParteDiarioByOt(id)
    }

    override fun getSupervisor(): LiveData<List<Supervisor>> {
        return dataBase.supervisorDao().getSupervisor()
    }

    override fun getOtSendParteDiario(): Observable<List<ParteDiario>> {
        return Observable.create { e ->
            val ot = dataBase.parteDiarioDao().getPrteDiarioTask(1)
            if (ot.isEmpty()) {
                e.onError(Throwable("Usted no tiene pendientes por enviar"))
                e.onComplete()
                return@create
            }
            e.onNext(ot)
            e.onComplete()
        }
    }

    override fun updateParteDiario(m: Mensaje): Completable {
        return Completable.fromAction {
            dataBase.parteDiarioDao().updateEnabledParteDiario(m.codigoBase, m.codigoRetorno)
        }
    }

    override fun sendParteDiario(body: RequestBody): Observable<Mensaje> {
        return apiService.sendParteDiario(body)
    }

    override fun saveGps(body: RequestBody): Call<Mensaje> {
        return apiService.saveGps(body)
    }

    override fun saveMovil(body: RequestBody): Call<Mensaje> {
        return apiService.saveMovil(body)
    }

    override fun addOtParteDiario(o: List<Ot>): Completable {
        return Completable.fromAction {
            for (t: Ot in o) {
                dataBase.otDao().updateOtParteDiario(t.otId)
            }
        }
    }

    override fun getInspecciones(): LiveData<List<InspeccionPoste>> {
        return dataBase.inspeccionPosteDao().getInspeccionPoste()
    }

    override fun getInspecciones(e: Int): LiveData<List<InspeccionPoste>> {
        return dataBase.inspeccionPosteDao().getInspeccionPoste(e)
    }

    override fun getEstadoPostes(): LiveData<List<EstadoPoste>> {
        return dataBase.estadoPosteDao().getEstados()
    }

    override fun getInspeccionById(id: Int): LiveData<InspeccionPoste> {
        return dataBase.inspeccionPosteDao().getInspeccionById(id)
    }

    override fun insertInspeccionPoste(p: InspeccionPoste): Completable {
        return Completable.fromAction {
            val i: InspeccionPoste? =
                dataBase.inspeccionPosteDao().getInspeccionByIdTask(p.inspeccionCampoId)
            if (i != null) {
                dataBase.inspeccionPosteDao().updateInspeccionPosteTask(p)
            }
        }
    }

    override fun getInspeccionConductorById(inspeccionId: Int): LiveData<InspeccionConductor> {
        return dataBase.inspeccionConductorDao().getInspeccionConductorById(inspeccionId)
    }

    override fun insertInspeccionConductor(p: InspeccionConductor): Completable {
        return Completable.fromAction {
            val i: InspeccionConductor? =
                dataBase.inspeccionConductorDao().getInspeccionByIdTask(p.inspeccionCampoId)
            if (i != null) {
                dataBase.inspeccionConductorDao().updateInspeccionConductorTask(p)
            } else {
                dataBase.inspeccionConductorDao().insertInspeccionConductorTask(p)
            }
        }
    }

    override fun getInspeccionCableById(inspeccionId: Int): LiveData<InspeccionCable> {
        return dataBase.inspeccionCableDao().getInspeccionCableById(inspeccionId)
    }

    override fun insertInspeccionCable(p: InspeccionCable): Completable {
        return Completable.fromAction {
            val i: InspeccionCable? =
                dataBase.inspeccionCableDao().getInspeccionByIdTask(p.inspeccionCampoId)
            if (i != null) {
                dataBase.inspeccionCableDao().updateInspeccionCableTask(p)
            } else {
                dataBase.inspeccionCableDao().insertInspeccionCableTask(p)
            }
        }
    }

    override fun getInspeccionEquipoById(inspeccionId: Int): LiveData<InspeccionEquipo> {
        return dataBase.inspeccionEquipoDao().getInspeccionEquipoById(inspeccionId)
    }

    override fun insertInspeccionEquipo(p: InspeccionEquipo): Completable {
        return Completable.fromAction {
            val i: InspeccionEquipo? =
                dataBase.inspeccionEquipoDao().getInspeccionByIdTask(p.inspeccionCampoId)
            if (i != null) {
                dataBase.inspeccionEquipoDao().updateInspeccionEquipoTask(p)
            } else {
                dataBase.inspeccionEquipoDao().insertInspeccionEquipoTask(p)
            }
        }
    }

    override fun getInspeccionesTask(): Observable<List<InspeccionPoste>> {
        return Observable.create { e ->
            val data: ArrayList<InspeccionPoste> = ArrayList()
            val ot = dataBase.inspeccionPosteDao().getInspeccionesTask(1)
            if (ot.isEmpty()) {
                e.onError(Throwable("Usted no tiene pendientes por enviar"))
                e.onComplete()
                return@create
            }
            for (p: InspeccionPoste in ot) {
                p.cable = dataBase.inspeccionCableDao().getInspeccionByIdTask(p.inspeccionCampoId)
                p.conductor =
                    dataBase.inspeccionConductorDao().getInspeccionByIdTask(p.inspeccionCampoId)
                p.equipo = dataBase.inspeccionEquipoDao().getInspeccionByIdTask(p.inspeccionCampoId)

                p.photos =
                    dataBase.inspeccionPhotoDao().getInspeccionPhotoByIdTask(p.inspeccionCampoId)

                data.add(p)
            }
            e.onNext(data)
            e.onComplete()
        }
    }

    override fun getInspeccionPhotoById(id: Int): LiveData<List<InspeccionPhoto>> {
        return dataBase.inspeccionPhotoDao().getInspeccionPhotoById(id)
    }

    override fun deleteInspeccionPhoto(o: InspeccionPhoto, context: Context): Completable {
        return Completable.fromAction {
            Util.deleteImage(o.fotoUrl, context)
            dataBase.inspeccionPhotoDao().deleteInspeccionPhotoTask(o)
        }
    }

    override fun insertInspeccionPhoto(o: InspeccionPhoto): Completable {
        return Completable.fromAction {
            val p: InspeccionPhoto? =
                dataBase.inspeccionPhotoDao().getInspeccionByUrlTask(o.fotoUrl)
            if (p == null) {
                dataBase.inspeccionPhotoDao().insertInspeccionPhotoTask(o)
            }
        }
    }

    override fun getInspeccionesPhotoTask(): Observable<List<InspeccionPhoto>> {
        return Observable.create { e ->
            val data: ArrayList<InspeccionPhoto> = ArrayList()
            val ot = dataBase.inspeccionPosteDao().getInspeccionesTask(1)
            if (ot.isEmpty()) {
                e.onError(Throwable("Usted no tiene pendientes por enviar"))
                e.onComplete()
                return@create
            }
            for (p: InspeccionPoste in ot) {
                val photos: List<InspeccionPhoto>? =
                    dataBase.inspeccionPhotoDao().getInspeccionPhotoByIdTask(p.inspeccionCampoId)
                if (photos != null) {
                    for (f: InspeccionPhoto in photos) {
                        data.add(f)
                    }
                }
            }
            e.onNext(data)
            e.onComplete()
        }
    }

    override fun sendInspeccionPhotos(body: RequestBody): Observable<String> {
        return apiService.sendInspeccionPhotos(body)
    }

    override fun sendInspecciones(body: RequestBody): Observable<Mensaje> {
        return apiService.sendInspecciones(body)
    }

    override fun updateInspeccion(m: Mensaje): Completable {
        return Completable.fromAction {
            dataBase.inspeccionPosteDao().updateEnabledInspeccion(m.codigoBase)
            dataBase.inspeccionCableDao().updateEnabledCable(m.codigoBase, m.codigoRetornoCable)
            dataBase.inspeccionConductorDao()
                .updateEnabledConductor(m.codigoBase, m.codigoRetornoConductor)
            dataBase.inspeccionEquipoDao().updateEnabledEquipo(m.codigoBase, m.codigoRetornoEquipo)
            dataBase.inspeccionPhotoDao().updateEnabledPhoto(m.codigoBase)
        }
    }

    override fun insertCabecera(o: OtCabecera): Observable<Int> {
        return Observable.create {
            val c: OtCabecera? =
                dataBase.otCabeceraDao().getOtCabeceraPerfil(o.otId, o.estadoPerfil)
            if (c == null) {
                val cabecera = dataBase.otCabeceraDao().getMaxIdOtTask()
                o.formatoId = if (cabecera != 0) {
                    cabecera + 1
                } else
                    1

                dataBase.otCabeceraDao().insertRegistroTask(o)
                it.onNext(o.formatoId)
                it.onComplete()
            } else {
                it.onNext(c.formatoId)
                it.onComplete()
            }
        }
    }
}