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

class RegistroViewModel @Inject
internal constructor(private val roomRepository: AppRepository, private val retrofit: ApiError) :
    ViewModel() {

    private val mensajeError = MutableLiveData<String>()
    private val mensajeSuccess: MutableLiveData<String> = MutableLiveData()
    val search: MutableLiveData<String> = MutableLiveData()
    val cabecera: MutableLiveData<Int> = MutableLiveData()
    val detalle: MutableLiveData<Int> = MutableLiveData()

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
            if (input == null || input.isEmpty()) {
                roomRepository.getOtByTipoPaging()
            } else {
                roomRepository.getOtByTipoPaging(
                    input //String.format("%s%s%s", "%", input, "%")
                )
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

    fun validateFormOne(d: OtDetalle) {
        insertOrUpdateOtDetalle(d)
    }

    fun validateFormTwo(d: OtDetalle) {
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
        insertOrUpdateHoja(o)
    }

    private fun insertOrUpdateHoja(o: OtCabecera) {
        roomRepository.insertOrUpdateHoja(o)
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

    fun getHojaById(id: Int): LiveData<OtCabecera> {
        return roomRepository.getHojaById(id)
    }
}