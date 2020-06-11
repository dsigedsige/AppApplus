package com.dsige.appapplus.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dsige.appapplus.data.local.model.Usuario
import com.dsige.appapplus.data.local.model.*
import com.dsige.appapplus.data.local.repository.ApiError
import com.dsige.appapplus.data.local.repository.AppRepository
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import rx.CompletableSubscriber
import rx.Scheduler
import javax.inject.Inject

class HojaViewModel @Inject
internal constructor(private val roomRepository: AppRepository, private val retrofit: ApiError) :
    ViewModel() {

    private val mensajeError = MutableLiveData<String>()
    private val mensajeSuccess: MutableLiveData<String> = MutableLiveData()

    val success: LiveData<String>
        get() = mensajeSuccess

    val error: LiveData<String>
        get() = mensajeError

    val user: LiveData<Usuario>
        get() = roomRepository.getUsuario()

    fun setError(s: String) {
        mensajeError.value = s
    }

    fun getGrupoById(id: Int): LiveData<List<Grupo>> {
        return roomRepository.getGrupoById(id)
    }

    fun validateHoja12(o: OtHoja123) {

        when (o.item) {
            1 -> {
                if (o.nroCelda.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Celda"
                    return
                }
                if (o.funcion.isEmpty()) {
                    mensajeError.value = "Ingrese Función"
                    return
                }
                if (o.cliente.isEmpty()) {
                    mensajeError.value = "Ingrese Enlace / Cliente"
                    return
                }
                if (o.suminisrro.isEmpty()) {
                    mensajeError.value = "Ingrese Suministro"
                    return
                }
            }
            2 -> {
                if (o.equipo.isEmpty()) {
                    mensajeError.value = "Ingrese Equipo"
                    return
                }
                if (o.subtipo.isEmpty()) {
                    mensajeError.value = "Ingrese Sub Tipo"
                    return
                }
                if (o.kardex.isEmpty()) {
                    mensajeError.value = "Ingrese Kardex"
                    return
                }
                if (o.nroFabrica.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Fabrica"
                    return
                }
                if (o.marca.isEmpty()) {
                    mensajeError.value = "Ingrese Marca"
                    return
                }
                if (o.modelo.isEmpty()) {
                    mensajeError.value = "Ingrese Modelo"
                    return
                }
                if (o.inom.isEmpty()) {
                    mensajeError.value = "Ingrese Inom"
                    return
                }
                if (o.mando.isEmpty()) {
                    mensajeError.value = "Ingrese Mando"
                    return
                }
                if (o.rele.isEmpty()) {
                    mensajeError.value = "Ingrese Rele"
                    return
                }
                if (o.irele.isEmpty()) {
                    mensajeError.value = "Ingrese Irele"
                    return
                }
            }
        }
        insertOrUpdteOtHoja123(o)
    }

    fun validateHoja3(o: OtHoja123) {
        if (o.equipo.isEmpty()) {
            mensajeError.value = "Ingrese Equipo"
            return
        }
        if (o.kardex.isEmpty()) {
            mensajeError.value = "Ingrese Nro Kardex"
            return
        }
        if (o.marca.isEmpty()) {
            mensajeError.value = "Ingrese Marca"
            return
        }
        if (o.tipo.isEmpty()) {
            mensajeError.value = "Ingrese Tipo"
            return
        }
        insertOrUpdteOtHoja123(o)
    }

    private fun insertOrUpdteOtHoja123(e: OtHoja123) {
        roomRepository.insertOrUpdteOtHoja123(e)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = if (e.hoja123Id == 0) "Guardado" else "Actualizado"
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }

            })
    }

    fun getHojaByTipo(tipo: Int, formatoId: Int): LiveData<List<Class<*>>> {
        return roomRepository.getHojaByTipo(tipo, formatoId)
    }

    fun getHoja123ById(id: Int): LiveData<OtHoja123> {
        return roomRepository.getHoja123ById(id)
    }

    fun validateHoja4(o: OtHoja4) {
        if (o.ubicacion.isEmpty()) {
            mensajeError.value = "Ingrese Ubicación"
            return
        }
        if (o.nroFabrica.isEmpty()) {
            mensajeError.value = "Ingrese Nro Fabrica"
            return
        }
        if (o.potInst.isEmpty()) {
            mensajeError.value = "Ingrese PostInst(KVA)"
            return
        }
        if (o.anio.isEmpty()) {
            mensajeError.value = "Ingrese Año"
            return
        }
        if (o.marca.isEmpty()) {
            mensajeError.value = "Ingrese Marca"
            return
        }
        if (o.tipo.isEmpty()) {
            mensajeError.value = "Ingrese Tipo"
            return
        }
        if (o.kardex.isEmpty()) {
            mensajeError.value = "Ingrese Nro de Kardex"
            return
        }
        if (o.relTransf.isEmpty()) {
            mensajeError.value = "Ingrese Rel Transf (V)"
            return
        }
        if (o.potenciaCC.isEmpty()) {
            mensajeError.value = "Ingrese Potencia de CC"
            return
        }
        if (o.nroFases.isEmpty()) {
            mensajeError.value = "Ingrese Nro de Fases"
            return
        }
        if (o.cableC1.isEmpty()) {
            mensajeError.value = "Ingrese Terna N°1"
            return
        }
        if (o.cableC2.isEmpty()) {
            mensajeError.value = "Ingrese Terna N°2"
            return
        }
        if (o.cableC3.isEmpty()) {
            mensajeError.value = "Ingrese Terna N°3"
            return
        }
        if (o.cableC4.isEmpty()) {
            mensajeError.value = "Ingrese Terna N°4"
            return
        }
        if (o.cableC5.isEmpty()) {
            mensajeError.value = "Ingrese Terna N°5"
            return
        }
        if (o.disMarca.isEmpty()) {
            mensajeError.value = "Ingrese Marca Disyuntor"
            return
        }
        if (o.disKardex.isEmpty()) {
            mensajeError.value = "Ingrese Kardex"
            return
        }
        if (o.disSerie.isEmpty()) {
            mensajeError.value = "Ingrese Serie"
            return
        }
        if (o.disIA.isEmpty()) {
            mensajeError.value = "Ingrese I(A)"
            return
        }
        insertOrUpdteOtHoja4(o)
    }

    private fun insertOrUpdteOtHoja4(e: OtHoja4) {
        roomRepository.insertOrUpdteOtHoja4(e)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = if (e.hoja4Id == 0) "Guardado" else "Actualizado"
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }

            })
    }

    fun getHoja4ById(id: Int): LiveData<OtHoja4> {
        return roomRepository.getHoja4ById(id)
    }

    fun getHoja56ById(id: Int): LiveData<OtHoja56> {
        return roomRepository.getHoja56ById(id)
    }

    fun validateHoja56(o: OtHoja56) {
        if (o.tipoTablero == 0) {
            mensajeError.value = "Seleccion algún tipo de tablero"
            return
        }

        if (o.nroMedidor.isEmpty()) {
            mensajeError.value = "Ingrese Nro"
            return
        }
        if (o.baseMovil.isEmpty()) {
            mensajeError.value = "Ingrese Base(A)"
            return
        }
        if (o.fusible.isEmpty()) {
            mensajeError.value = "Ingrese Fusible(A)"
            return
        }
        if (o.seccion.isEmpty()) {
            mensajeError.value = "Ingrese Sección(mm2)"
            return
        }
        if (o.observacion.isEmpty()) {
            mensajeError.value = "Ingrese Observacion"
            return
        }
        insertOrUpdteOtHoja56(o)
    }

    fun validateHoja7(o: OtHoja56) {

        if (o.nroMedidor.isEmpty()) {
            mensajeError.value = "Ingrese Nro Medidor"
            return
        }
        if (o.fotocelula.isEmpty()) {
            mensajeError.value = "Ingrese Foto Celular"
            return
        }
        if (o.contactor.isEmpty()) {
            mensajeError.value = "Ingrese Contactor"
            return
        }
        if (o.intHorario.isEmpty()) {
            mensajeError.value = "Ingrese Int Horario"
            return
        }
        if (o.observacion.isEmpty()) {
            mensajeError.value = "Ingrese Observación"
            return
        }

        insertOrUpdteOtHoja56(o)
    }

    private fun insertOrUpdteOtHoja56(e: OtHoja56) {
        roomRepository.insertOrUpdteOtHoja56(e)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = if (e.hoja56Id == 0) "Guardado" else "Actualizado"
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }

            })
    }

    fun getHoja123ByItem(item: Int, formatoId: Int): LiveData<OtHoja123> {
        return roomRepository.getHoja123ByItem(item, formatoId)
    }

    fun getHoja567ByItem(item: Int, formatoId: Int): LiveData<OtHoja56> {
        return roomRepository.getHoja567ByItem(item, formatoId)
    }

    fun getHojaByItem(item: Int, formatoId: Int): LiveData<Class<*>> {
        return roomRepository.getHojaByItem(item, formatoId)
    }
}