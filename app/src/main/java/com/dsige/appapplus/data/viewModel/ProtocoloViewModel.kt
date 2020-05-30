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

    fun validateProtocolo(e: OtProtocolo) {
        insertOrUpdteOtOtProtocolo(e)
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
}