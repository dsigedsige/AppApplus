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

    fun validateHoja123(e: OtHoja123) {
        insertOrUpdteOtHoja123(e)
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
}