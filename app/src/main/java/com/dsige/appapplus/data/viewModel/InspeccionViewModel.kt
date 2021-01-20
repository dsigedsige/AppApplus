package com.dsige.appapplus.data.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.dsige.appapplus.data.local.model.Usuario
import com.dsige.appapplus.data.local.model.*
import com.dsige.appapplus.data.local.repository.ApiError
import com.dsige.appapplus.data.local.repository.AppRepository
import com.dsige.appapplus.helper.Mensaje
import com.dsige.appapplus.helper.Util
import com.google.gson.Gson
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InspeccionViewModel @Inject
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

    fun getGrupoById(id: Int): LiveData<List<Grupo>> {
        return roomRepository.getGrupoById(id)
    }

    fun getInspecciones(): LiveData<List<InspeccionPoste>> {
        return Transformations.switchMap(search) { input ->
            if (input.isEmpty()) {
                roomRepository.getInspecciones()
            } else {
                val f = Gson().fromJson(input, Filtro::class.java)
                roomRepository.getInspecciones(f.pageSize)
            }
        }
    }

    fun getEstados(): LiveData<List<EstadoPoste>> {
        return roomRepository.getEstadoPostes()
    }

    fun getInspeccionById(id: Int): LiveData<InspeccionPoste> {
        return roomRepository.getInspeccionById(id)
    }

    fun validateFormGeneral(p: InspeccionPoste) {

        insertInspeccionPoste(p)
    }

    fun validatePoste1(p: InspeccionPoste) {
        insertInspeccionPoste(p)
    }

//    fun validatePoste2(p: InspeccionPoste) {
//        insertInspeccionPoste(p)
//    }

    fun validatePoste3(p: InspeccionPoste) {
        insertInspeccionPoste(p)
    }

    fun validatePoste6(p: InspeccionPoste) {
        insertInspeccionPoste(p)
    }

    fun validatePoste8(p: InspeccionPoste) {
        insertInspeccionPoste(p)
    }

    fun validatePoste9(p: InspeccionPoste) {
        insertInspeccionPoste(p)
    }

    private fun insertInspeccionPoste(p: InspeccionPoste) {
        roomRepository.insertInspeccionPoste(p)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    mensajeSuccess.value = "Actualizado"
                }
            })
    }

    fun getInspeccionConductorById(inspeccionId: Int): LiveData<InspeccionConductor> {
        return roomRepository.getInspeccionConductorById(inspeccionId)
    }

    fun validatePoste2(p: InspeccionConductor) {
        insertInspeccionConductor(p)
    }

    private fun insertInspeccionConductor(p: InspeccionConductor) {
        roomRepository.insertInspeccionConductor(p)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    mensajeSuccess.value = "Actualizado"
                }
            })
    }

    fun getInspeccionCableById(inspeccionId: Int): LiveData<InspeccionCable> {
        return roomRepository.getInspeccionCableById(inspeccionId)
    }

    fun validatePoste4(p: InspeccionCable) {
        insertInspeccionCable(p)
    }

    fun validatePoste5(p: InspeccionCable) {
        insertInspeccionCable(p)
    }

    private fun insertInspeccionCable(p: InspeccionCable) {
        roomRepository.insertInspeccionCable(p)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    mensajeSuccess.value = "Actualizado"
                }
            })
    }

    fun getInspeccionEquipoById(inspeccionId: Int): LiveData<InspeccionEquipo> {
        return roomRepository.getInspeccionEquipoById(inspeccionId)
    }

    fun validatePoste7(p: InspeccionEquipo) {
        insertInspeccionEquipo(p)
    }

    private fun insertInspeccionEquipo(p: InspeccionEquipo) {
        roomRepository.insertInspeccionEquipo(p)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    mensajeSuccess.value = "Actualizado"
                }
            })
    }

    fun getInspeccionPhotoById(id: Int): LiveData<List<InspeccionPhoto>> {
        return roomRepository.getInspeccionPhotoById(id)
    }

    fun deletePhoto(o: InspeccionPhoto, context: Context) {
        return roomRepository.deleteInspeccionPhoto(o, context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    mensajeError.value = "Eliminado"
                }
            })
    }

    fun insertInspeccionPhoto(o: InspeccionPhoto) {
        roomRepository.insertInspeccionPhoto(o)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    mensajeSuccess.value = "Guardado"
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
}