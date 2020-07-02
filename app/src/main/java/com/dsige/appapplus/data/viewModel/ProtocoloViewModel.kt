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

class ProtocoloViewModel @Inject
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

    fun validateProtocoloP1(o: OtProtocolo) {
//        if(o.tipoTerreno.isEmpty()){
//            mensajeError.value = "Ingrese Tipo Terreno"
//            return
//        }
//        if(o.estadoTerreno.isEmpty()){
//            mensajeError.value = "Ingrese Estado Terreno"
//            return
//        }
//        if(o.resistenciaSeca.isEmpty()){
//            mensajeError.value = "Ingrese Resistencia Promedio(Seca)"
//            return
//        }
//        if(o.resistenciaHumeda.isEmpty()){
//            mensajeError.value = "Ingrese Resistencia Promedio(Humeda)"
//            return
//        }
//        if(o.fechaSeca.isEmpty()){
//            mensajeError.value = "Ingrese Fecha Medición(Seca)"
//            return
//        }
//        if(o.aSeca1.isEmpty()){
//            mensajeError.value = "Ingrese A=1"
//            return
//        }
//        if(o.aSeca2.isEmpty()){
//            mensajeError.value = "Ingrese A=2"
//            return
//        }
//        if(o.aSeca3.isEmpty()){
//            mensajeError.value = "Ingrese A=3"
//            return
//        }
//        if(o.fechaHumeda.isEmpty()){
//            mensajeError.value = "Ingrese Fecha Medición(Humeda)"
//            return
//        }
//        if(o.aHumeda1.isEmpty()){
//            mensajeError.value = "Ingrese A=1"
//            return
//        }
//        if(o.aHumeda2.isEmpty()){
//            mensajeError.value = "Ingrese A=2"
//            return
//        }
//        if(o.aHumeda3.isEmpty()){
//            mensajeError.value = "Ingrese A=3"
//            return
//        }
        insertOrUpdteOtOtProtocolo(o)
    }

    fun validateProtocoloP24(o: OtProtocolo) {
//        if(o.rBt1.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pBt1.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rBt2.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pBt2.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rBt3.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pBt3.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rBt4.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pBt4.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rBtE.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pBtE.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rMt1.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pMt1.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rMt2.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pMt2.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rMt3.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pMt3.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rMt4.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pMt4.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.rMtE.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.pMtE.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
//        if(o.observaciones.isEmpty()){
//            mensajeError.value = "Ingrese todo los campos"
//            return
//        }
        insertOrUpdteOtOtProtocolo(o)
    }

    fun validateProtocoloP3(o: OtProtocolo) {
//        if(o.proyecto.isEmpty()){
//            mensajeError.value = "Ingrese Proyecto/Orden Trabajo"
//            return
//        }
//        if(o.tipoSistema.isEmpty()){
//            mensajeError.value = "Ingrese Tipo de Sistema"
//            return
//        }
//        if(o.grupoResis.isEmpty()){
//            mensajeError.value = "Ingrese Grupo Resistividad"
//            return
//        }
//        if(o.nivelResis.isEmpty()){
//            mensajeError.value = "Ingrese Nivel Resistividad"
//            return
//        }
//        if(o.btNpozos.isEmpty()){
//            mensajeError.value = "Ingrese Nº Pozos BT"
//            return
//        }
//        if(o.btNdosis.isEmpty()){
//            mensajeError.value = "Ingrese Nº Dosis por Pozo"
//            return
//        }
//        if(o.btCompuesto.isEmpty()){
//            mensajeError.value = "Ingrese Compuesto Quimico Tratante"
//            return
//        }
//        if(o.mtNpozos.isEmpty()){
//            mensajeError.value = "Ingrese Nº Pozos MT"
//            return
//        }
//        if(o.mtNdosis.isEmpty()){
//            mensajeError.value = "Ingrese Nº Dosis por Pozo"
//            return
//        }
//        if(o.mtCompuesto.isEmpty()){
//            mensajeError.value = "Ingrese Compuesto Quimico Tratante"
//            return
//        }
//        if(o.observaciones.isEmpty()){
//            mensajeError.value = "Ingrese Observaciones"
//            return
//        }
        insertOrUpdteOtOtProtocolo(o)
    }

    private fun insertOrUpdteOtOtProtocolo(e: OtProtocolo) {
        roomRepository.insertOrUpdteOtOtProtocolo(e)
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

    fun getEquipoByTipo(tipo: Int, formatoId: Int): LiveData<List<OtEquipo>> {
        return roomRepository.getEquipoByTipo(tipo, formatoId)
    }

    fun getEquipoById(id: Int): LiveData<OtEquipo> {
        return roomRepository.getEquipoById(id)
    }

    fun getProtocoloByTipo(formatoId: Int, tipo: Int): LiveData<OtProtocolo> {
        return roomRepository.getProtocoloByTipo(formatoId, tipo)
    }

    fun getPuestoTierra(): LiveData<List<PuestoTierra>> {
        return roomRepository.getPuestoTierra()
    }
}