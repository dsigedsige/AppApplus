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

class EquipoViewModel @Inject
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

    fun validateEquipo(e: OtEquipo) {

        when (e.tipoEquipo) {
            1 -> {
                if (e.nroKardex.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Kardex"
                    return
                }
                if (e.nroFabrica.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Fabrica"
                    return
                }
                if (e.sedUbicacion.isEmpty()) {
                    mensajeError.value = "Ingrese Sed"
                    return
                }
                if (e.celdaUbicacion.isEmpty()) {
                    mensajeError.value = "Ingrese Celda"
                    return
                }
                if (e.potenciaKVA.isEmpty()) {
                    mensajeError.value = "Ingrese Potencia KVA"
                    return
                }
                if (e.anio.isEmpty()) {
                    mensajeError.value = "Ingrese Año"
                    return
                }
                if (e.marca.isEmpty()) {
                    mensajeError.value = "Ingrese Marca"
                    return
                }
                if (e.tipo.isEmpty()) {
                    mensajeError.value = "Ingrese Tipo"
                    return
                }
                if (e.destino.isEmpty()) {
                    mensajeError.value = "Ingrese Destino"
                    return
                }
                if (e.observacion.isEmpty()) {
                    mensajeError.value = "Ingrese Observación"
                    return
                }
            }
            2 -> {
                if (e.nroKardex.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Kardex"
                    return
                }
                if (e.nroFabrica.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Fabrica"
                    return
                }
                if (e.sedUbicacion.isEmpty()) {
                    mensajeError.value = "Ingrese Sed"
                    return
                }
                if (e.celdaUbicacion.isEmpty()) {
                    mensajeError.value = "Ingrese Celda"
                    return
                }
                if (e.funcionCelda.isEmpty()) {
                    mensajeError.value = "Ingrese Función de Celda"
                    return
                }
                if (e.enlace.isEmpty()) {
                    mensajeError.value = "Ingrese Enlace"
                    return
                }
                if (e.destino.isEmpty()) {
                    mensajeError.value = "Ingrese Destino"
                    return
                }
                if (e.observacion.isEmpty()) {
                    mensajeError.value = "Ingrese Observación"
                    return
                }
            }
            3 -> {
                if (e.equipo.isEmpty()) {
                    mensajeError.value = "Ingrese Equipo"
                    return
                }
                if (e.nroKardex.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Kardex"
                    return
                }
                if (e.marca.isEmpty()) {
                    mensajeError.value = "Ingrese Marca"
                    return
                }
                if (e.tipo.isEmpty()) {
                    mensajeError.value = "Ingrese Tipo"
                    return
                }
            }
            4 -> {
                if (e.nroKardex.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Kardex"
                    return
                }
                if (e.nroFabrica.isEmpty()) {
                    mensajeError.value = "Ingrese Nro Fabrica"
                    return
                }
                if (e.nroPrc.isEmpty()) {
                    mensajeError.value = "Ingrese Nro PRC"
                    return
                }
                if (e.soporte.isEmpty()) {
                    mensajeError.value = "Ingrese Soporte"
                    return
                }
                if (e.destino.isEmpty()) {
                    mensajeError.value = "Ingrese Destino"
                    return
                }
                if (e.observacion.isEmpty()) {
                    mensajeError.value = "Ingrese Observaciones"
                    return
                }
            }
        }
        insertOrUpdteOtEquipo(e)
    }

    private fun insertOrUpdteOtEquipo(e: OtEquipo) {
        roomRepository.insertOrUpdteOtEquipo(e)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    mensajeSuccess.value = if (e.equipoId == 0) "Guardado" else "Actualizado"
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }

            })

    }

    fun getEquipoByTipo(tipo: Int, formatoId: Int): LiveData<List<OtEquipo>> {
        return roomRepository.getEquipoByTipo(tipo, formatoId)
    }

    fun getEquipoById(id: Int): LiveData<OtEquipo> {
        return roomRepository.getEquipoById(id)
    }
}