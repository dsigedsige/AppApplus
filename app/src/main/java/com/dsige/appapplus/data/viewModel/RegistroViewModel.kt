package com.dsige.appapplus.data.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.dsige.appapplus.data.local.model.Usuario
import com.dsige.appapplus.data.local.model.*
import com.dsige.appapplus.data.local.repository.ApiError
import com.dsige.appapplus.data.local.repository.AppRepository
import com.dsige.appapplus.helper.Mensaje
import com.dsige.appapplus.helper.Util
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegistroViewModel @Inject
internal constructor(private val roomRepository: AppRepository, private val retrofit: ApiError) :
    ViewModel() {

    private val mensajeError = MutableLiveData<String>()
    private val mensajeSuccess: MutableLiveData<String> = MutableLiveData()
    val search: MutableLiveData<String> = MutableLiveData()
    val cabecera: MutableLiveData<Int> = MutableLiveData()
    val detalle: MutableLiveData<Int> = MutableLiveData()
    val mensaje: MutableLiveData<Mensaje> = MutableLiveData()

    val ots = ArrayList<Ot>()
    val otsData = MutableLiveData<List<Ot>>()

    fun setOts(o: Ot) {
        ots.add(o)
        otsData.value = ots
    }

    fun removeItemAt(o: Ot) {
        val oldValue = ots
        oldValue.remove(o)
        otsData.value = oldValue
    }

    fun removeAll() {
        ots.clear()
        otsData.value = mutableListOf()
    }

    val success: LiveData<String>
        get() = mensajeSuccess

    val error: LiveData<String>
        get() = mensajeError

    val user: LiveData<Usuario>
        get() = roomRepository.getUsuario()

    fun setError(s: String) {
        mensajeError.value = s
    }

    fun getOtByTipoPaging(): LiveData<PagedList<Ot>> {
        return Transformations.switchMap(search) { input ->
            if (input.isEmpty() || input == null) {
                roomRepository.getOtByTipoPaging()
            } else {
                val f = Gson().fromJson(input, Filtro::class.java)
                if (f.pageSize != 0) {
                    if (f.search.isEmpty()) {
                        roomRepository.getOtByTipoPaging(
                            f.pageSize //String.format("%s%s%s", "%", input, "%")
                        )
                    } else {
                        roomRepository.getOtByTipoPaging(
                            f.pageSize, String.format("%s%s%s", "%", f.search, "%")
                        )
                    }
                } else {
                    roomRepository.getOtByTipoPaging(String.format("%s%s%s", "%", f.search, "%"))
                }
            }
        }
    }

    fun getOtCabeceraByTipoPaging(id: Int): LiveData<PagedList<OtCabecera>> {
        return Transformations.switchMap(cabecera) { input ->
            if (input == 0) {
                roomRepository.getOtCabeceraByTipoPaging(id)
            } else {
                roomRepository.getOtCabeceraByTipoPaging(
                    id,
                    input //String.format("%s%s%s", "%", input, "%")
                )
            }
        }
    }

    fun getOtDetalleByTipoPaging(): LiveData<PagedList<OtDetalle>> {
        return Transformations.switchMap(detalle) { input ->
            if (input == 0) {
                roomRepository.getOtDetalleByTipoPaging()
            } else {
                roomRepository.getOtDetalleByTipoPaging(
                    input //String.format("%s%s%s", "%", input, "%")
                )
            }
        }
    }

    fun getOtById(id: Int): LiveData<Ot> {
        return roomRepository.getOtById(id)
    }

    fun getFormato(): LiveData<List<Formato>> {
        return roomRepository.getFormato()
    }

    fun getOtCabeceraById(id: Int): LiveData<OtCabecera> {
        return roomRepository.getOtCabeceraById(id)
    }

    fun getOtDetalleById(id: Int): LiveData<List<OtDetalle>> {
        return roomRepository.getOtDetalleById(id)
    }

    fun getFormById(id: Int): LiveData<OtDetalle> {
        return roomRepository.getFormById(id)
    }

    fun validateFormBTOne(d: OtDetalle) {
//        if (d.codigoSoporte.isEmpty()) {
//            mensajeError.value = "Ingrese Codigo AP/Correlativo"
//            return
//        }
//        if (d.codigoVia.isEmpty()) {
//            mensajeError.value = "Ingrese Codigo Via"
//            return
//        }
//        if (d.llave.isEmpty()) {
//            mensajeError.value = "Ingrese Llave"
//            return
//        }
//        if (d.sistemas.isEmpty()) {
//            mensajeError.value = "Ingrese Sistema MT"
//            return
//        }
//        if (d.nombreTipoMaterialId.isEmpty()) {
//            mensajeError.value = "Seleccione Material de Soporte "
//            return
//        }
//        if (d.tamanio.isEmpty()) {
//            mensajeError.value = "Seleccione Tamaño "
//            return
//        }
//        if (d.redSDS.isEmpty()) {
//            mensajeError.value = "Ingrese SP"
//            return
//        }
//        if (d.redAP.isEmpty()) {
//            mensajeError.value = "Ingrese AP"
//            return
//        }
//        if (d.redAmbas.isEmpty()) {
//            mensajeError.value = "Ingrese MT"
//            return
//        }
//        if (d.cajaDeriva.isEmpty()) {
//            mensajeError.value = "Ingrese Caja Deriva"
//            return
//        }
//        if (d.retenidaV.isEmpty()) {
//            mensajeError.value = "Ingrese Retenidas V"
//            return
//        }
//        if (d.retenidaS.isEmpty()) {
//            mensajeError.value = "Ingrese Retenidas S"
//            return
//        }
        insertOrUpdateOtDetalle(d)
    }

    fun validateFormBTTwo(d: OtDetalle) {
//        if (d.pastotalC.isEmpty()) {
//            mensajeError.value = "Ingrese Pastotal C"
//            return
//        }
//        if (d.pastotalGF.isEmpty()) {
//            mensajeError.value = "Ingrese Pastotal GF"
//            return
//        }
//        if (d.equipoTipo.isEmpty()) {
//            mensajeError.value = "Ingrese Tipo de Equipo"
//            return
//        }
//        if (d.equipoModelo.isEmpty()) {
//            mensajeError.value = "Ingrese Modelo"
//            return
//        }
//        if (d.lampara.isEmpty()) {
//            mensajeError.value = "Ingrese Tipo de Lampara"
//            return
//        }
//        if (d.direccion.isEmpty()) {
//            mensajeError.value = "Ingrese Dirección"
//            return
//        }
        insertOrUpdateOtDetalle(d)
    }

    fun validateFormMTOne(d: OtDetalle) {
//        if (d.codigoSoporte.isEmpty()) {
//            mensajeError.value = "Ingrese Codigo de Soporte"
//            return
//        }
//        if (d.alim.isEmpty()) {
//            mensajeError.value = "Ingrese Alim"
//            return
//        }
//        if (d.armado.isEmpty()) {
//            mensajeError.value = "Ingrese Armado"
//            return
//        }
//        if (d.nombreTipoMaterialId.isEmpty()) {
//            mensajeError.value = "Ingrese Material"
//            return
//        }
//        if (d.tamanio.isEmpty()) {
//            mensajeError.value = "Ingrese Tamaño"
//            return
//        }
//        if (d.nombreFuncionId.isEmpty()) {
//            mensajeError.value = "Ingrese Función"
//            return
//        }
//
//        if (d.cNumeroId == 0) {
//            mensajeError.value = "Ingrese Nro de Seccionamiento"
//            return
//        }
//        if (d.seccCod.isEmpty()) {
//            mensajeError.value = "Ingrese Cod"
//            return
//        }
//        if (d.seccCap.isEmpty()) {
//            mensajeError.value = "Ingrese Cap"
//            return
//        }
//        if (d.seccFus.isEmpty()) {
//            mensajeError.value = "Ingrese Fus"
//            return
//        }
//        if (d.nombreTipoConductorId.isEmpty()) {
//            mensajeError.value = "Seleccione Tipo de Conductor "
//            return
//        }
//        if (d.lvano.isEmpty()) {
//            mensajeError.value = "Ingrese L_Vano(m)"
//            return
//        }
//        if (d.conduSecc.isEmpty()) {
//            mensajeError.value = "Ingrese Sección"
//            return
//        }
//        if (d.conduFases.isEmpty()) {
//            mensajeError.value = "Ingrese Fase"
//            return
//        }
        insertOrUpdateOtDetalle(d)
    }

    fun validateFormMTTwo(d: OtDetalle) {
//        if (d.tipoAisladorId.isEmpty()) {
//            mensajeError.value = "Ingrese Tipo Aislador"
//            return
//        }
//        if (d.aislaMaterial.isEmpty()) {
//            mensajeError.value = "Ingrese Material Aislador"
//            return
//        }
//        if (d.aislaCantidad.isEmpty()) {
//            mensajeError.value = "Ingrese Cantidad Aislador"
//            return
//        }
//        if (d.vientoViolin.isEmpty()) {
//            mensajeError.value = "Ingrese Viento"
//            return
//        }
//        if (d.vientoSimple.isEmpty()) {
//            mensajeError.value = "Ingrese Viento Simple"
//            return
//        }
//        if (d.vientoCantidad.isEmpty()) {
//            mensajeError.value = "Ingrese Cantidad"
//            return
//        }
//        if (d.pastoral.isEmpty()) {
//            mensajeError.value = "Ingrese Pastoral"
//            return
//        }
//        if (d.observaciones.isEmpty()) {
//            mensajeError.value = "Ingrese Observaciones"
//            return
//        }
        insertOrUpdateOtDetalle(d)
    }

    private fun insertOrUpdateOtDetalle(d: OtDetalle) {
        roomRepository.insertOrUpdateOtDetalle(d)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = "Guardado"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }

    fun getMaxIdOt(): LiveData<Int> {
        return roomRepository.getMaxIdOt()
    }

    fun getMaxIdOtDetalle(): LiveData<Int> {
        return roomRepository.getMaxIdOtDetalle()
    }

    fun generateCabecera(title: String, tipo: Int, codigo: String, id: Int, otId: Int) {
        roomRepository.generateCabecera(title, tipo, codigo, id, otId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = "Generado"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }

    fun getGrupoById(id: Int): LiveData<List<Grupo>> {
        return roomRepository.getGrupoById(id)
    }

    fun validateHoja(o: OtCabecera) {
        roomRepository.findSed(o.sed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: String) {
                    o.alimentador = t
                    insertOrUpdateCabecera(0, o)
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }

    private fun insertOrUpdateCabecera(tipo: Int, o: OtCabecera) {
        roomRepository.insertOrUpdateCabecera(o)
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    when (tipo) {
                        1 -> mensaje.value = Mensaje(o.formatoId, o.tipoFormatoId.toString())
                        else -> mensajeSuccess.value = o.tipoFormatoId.toString()
                    }
                }

                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }

    fun validateCabeceraPerfil(o: OtCabecera) {
        roomRepository.insertCabecera(o)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(t: Int) {
                    mensaje.value = Mensaje(t, o.otId)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    fun getHojaById(id: Int): LiveData<OtCabecera> {
        return roomRepository.getHojaById(id)
    }

    fun validateCabeceraMTBTEquipo(o: OtCabecera) {
        roomRepository.findSed(o.sed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: String) {
                    o.alimentador = t
                    insertOrUpdateCabecera(1, o)
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }

    fun validateProtocolo(o: OtCabecera) {
        roomRepository.findSed(o.sed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: String) {
                    o.alimentador = t
                    insertOrUpdateCabecera(0, o)
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }

    fun getEstados(): LiveData<List<EstadoTrabajo>> {
        return roomRepository.getEstados()
    }

    fun getPhotoById(id: Int): LiveData<List<OtPhoto>> {
        return roomRepository.getPhotoById(id)
    }

    fun validateOtPhoto(o: OtPhoto) {
//        if (o.observacion.isEmpty()){
//            mensajeError.value = "Ingresar una observacion"
//            return
//        }
        insertOrUpdatePhoto(o)
    }

    private fun insertOrUpdatePhoto(o: OtPhoto) {
        roomRepository.insertOrUpdatePhoto(o)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = "Ok"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }

    fun deletePhoto(o: OtPhoto) {
        roomRepository.deletePhoto(o)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeError.value = "Eliminado"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun generarArchivo(nameImg: String, context: Context, data: Intent) {
        Util.getFolderAdjunto(nameImg, context, data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = nameImg
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Log.i("TAG", e.toString())
                }
            })
    }

    fun changeEstado(id: Int, estado: Int) {
        roomRepository.changeEstado(id, estado)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun getCadistas(): LiveData<List<Cadista>> {
        return roomRepository.getCadistas()
    }

    fun validateParteDiario(p: ParteDiario) {
//        roomRepository.findSed(p.nroSed)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<String> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: String) {
//                    insertOrUpdateParteDiario(p)
//                }
//
//                override fun onError(e: Throwable) {
//                    mensajeError.value = e.message
//                }
//            })

        insertOrUpdateParteDiario(p)
    }

    private fun insertOrUpdateParteDiario(p: ParteDiario) {
        roomRepository.insertOrUpdateParteDiario(p)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    if (p.parteDiarioId == 0)
                        mensajeSuccess.value = "Guardado"
                    else
                        mensajeSuccess.value = "Actualizado"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }

    fun getParteDiarioByOt(id: Int): LiveData<ParteDiario> {
        return roomRepository.getParteDiarioByOt(id)
    }

    fun getSupervisor(): LiveData<List<Supervisor>> {
        return roomRepository.getSupervisor()
    }

    fun sendEstadoOt(o: Ot, estado: Int) {
        roomRepository.updateEstadoOt(o.otId, estado)
            .subscribeOn(Schedulers.io())
            .delay(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Mensaje> {

                override fun onSubscribe(d: Disposable) {
                    Log.i("TAG", d.toString())
                }

                override fun onNext(m: Mensaje) {
                    changeEstado(o.otId, estado)
                }

                override fun onError(e: Throwable) {
                    if (e is HttpException) {
                        val body = e.response().errorBody()
                        try {
                            val error = retrofit.errorConverter.convert(body!!)
                            mensajeError.postValue(error!!.Message)
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                            Log.i("TAG", e1.toString())
                        }
                    } else {
                        mensajeError.postValue(e.message)
                    }
                }

                override fun onComplete() {
                    removeItemAt(o)
                    mensajeSuccess.value = "Actualizado"
                }
            })
    }

    fun changeMasiveEstado(estado: Int) {
        val data: Observable<List<Ot>> = Observable.create { e ->
            e.onNext(otsData.value!!)
            e.onComplete()
        }
        data.flatMap { observable ->
            Observable.fromIterable(observable).flatMap { a ->
                Observable.zip(
                    Observable.just(a),
                    roomRepository.updateEstadoOt(a.otId, estado),
                    { _, mensaje ->
                        mensaje
                    })
            }
        }.subscribeOn(Schedulers.io())
            .delay(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Mensaje> {

                override fun onSubscribe(d: Disposable) {
                    Log.i("TAG", d.toString())
                }

                override fun onNext(m: Mensaje) {
                    changeEstado(m.codigoBase, estado)
                }

                override fun onError(e: Throwable) {
                    if (e is HttpException) {
                        val body = e.response().errorBody()
                        try {
                            val error = retrofit.errorConverter.convert(body!!)
                            mensajeError.postValue(error!!.Message)
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                            Log.i("TAG", e1.toString())
                        }
                    } else {
                        mensajeError.postValue(e.message)
                    }
                }

                override fun onComplete() {
                    mensajeSuccess.value = "Datos Actualizados"
                }
            })
    }

    fun addOtParteDiario() {
        roomRepository.addOtParteDiario(otsData.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = "PD"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }

}