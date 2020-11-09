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

}