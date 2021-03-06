package com.dsige.appapplus.data.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsige.appapplus.data.local.model.Sync
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
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UsuarioViewModel @Inject
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

    fun getLogin(usuario: String, pass: String, imei: String, version: String) {
        roomRepository.getUsuarioService(usuario, pass, imei, version)
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Usuario> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(usuario: Usuario) {
                    insertUsuario(usuario, version)
                }

                override fun onError(t: Throwable) {
                    if (t is HttpException) {
                        val body = t.response().errorBody()
                        try {
                            val error = retrofit.errorConverter.convert(body!!)
                            mensajeError.postValue(error!!.Message)
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                        }
                    } else {
                        mensajeError.postValue(t.message)
                    }
                }

                override fun onComplete() {
                }
            })
    }

    fun insertUsuario(u: Usuario, v: String) {
        roomRepository.insertUsuario(u)
            .delay(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    sync(u.usuarioId, v)
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.toString()
                }
            })
    }

    fun sync(id: Int, v: String) {
        roomRepository.getSync(id, v)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Sync> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Sync) {
                    saveSync(t)
                }

                override fun onError(t: Throwable) {
                    if (t is HttpException) {
                        val body = t.response().errorBody()
                        try {
                            val error = retrofit.errorConverter.convert(body!!)
                            mensajeError.postValue(error!!.Message)
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                        }
                    } else {
                        mensajeError.postValue(t.message)
                    }
                }

            })
    }

    private fun saveSync(s: Sync) {
        roomRepository.saveSync(s)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    mensajeSuccess.value = "Sincronizacón Completa"
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.toString()
                }
            })
    }

    fun logout(context: Context) {
        roomRepository.deleteUsuario(context)
            .delay(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {
                    mensajeSuccess.value = "Close"
                }

                override fun onError(e: Throwable) {
                    mensajeError.value = e.toString()
                }
            })
    }

    fun sendTrabajo(context: Context) {
        val ot: Observable<List<OtCabecera>> = roomRepository.getOtCabeceraBySend(1)
        ot.flatMap { observable ->
            Observable.fromIterable(observable).flatMap { a ->
                val b = MultipartBody.Builder()
                val vale: List<OtPhoto>? = a.photos
                if (vale != null) {
                    for (v: OtPhoto in vale) {
                        if (v.fotoUrl.isNotEmpty()) {
                            val file = File(Util.getFolder(context), v.fotoUrl)
                            if (file.exists()) {
                                b.addFormDataPart(
                                    "files", file.name,
                                    RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        file
                                    )
                                )
                            }
                        }
                    }
                }

                val json = Gson().toJson(a)
                Log.i("TAG", json)
                b.setType(MultipartBody.FORM)
                b.addFormDataPart("data", json)

                val body = b.build()
                Observable.zip(
                    Observable.just(a), roomRepository.saveTrabajo(body), { _, mensaje ->
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
                    Log.i("TAG", "RECIBIENDO LOS DATOS")
                    updateRegistro(m)
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
                    mensajeSuccess.value = "Datos enviados"
                }
            })
    }

    private fun updateRegistro(messages: Mensaje) {
        roomRepository.updateRegistro(messages)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    Log.i("TAG", e.toString())
                }
            })
    }

    fun sendReasignaciones() {
        val ot: Observable<List<Ot>> = roomRepository.getOtSend(1)
        ot.flatMap { observable ->
            Observable.fromIterable(observable).flatMap { a ->
                val json = Gson().toJson(a)
                Log.i("TAG", json)
                val body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
                Observable.zip(
                    Observable.just(a), roomRepository.updateOt(body), { _, mensaje ->
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
                    Log.i("TAG", "RECIBIENDO LOS DATOS")
                    updateOt(m)
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
                    mensajeSuccess.value = "Reasignación Completa"
                }
            })
    }

    fun updateOtReasignacion(o: Ot, tipo: Boolean) {
        roomRepository.updateOtReasignacion(o)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    if (tipo)
                        sendReasignacion(o.otId)
                    else
                        mensajeSuccess.value = "Guardado"
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Log.i("TAG", e.toString())
                }
            })
    }

    private fun sendReasignacion(id: Int) {
        val ot: Observable<Ot> = roomRepository.getOtSendById(id)
        ot.flatMap { a ->
            val json = Gson().toJson(a)
            Log.i("TAG", json)
            val body =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            Observable.zip(
                Observable.just(a), roomRepository.updateOt(body),
                { _, mensaje ->
                    mensaje
                })
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Mensaje> {

                override fun onSubscribe(d: Disposable) {
                    Log.i("TAG", d.toString())
                }

                override fun onNext(m: Mensaje) {
                    Log.i("TAG", "RECIBIENDO LOS DATOS")
                    updateOt(m)
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
                }
            })
    }

    private fun updateOt(m: Mensaje) {
        roomRepository.updateOtById(m)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
            })
    }

    fun sendParteDiarios() {
        val ot: Observable<List<ParteDiario>> = roomRepository.getOtSendParteDiario()
        ot.flatMap { observable ->
            Observable.fromIterable(observable).flatMap { a ->
                val json = Gson().toJson(a)
                Log.i("TAG", json)
                val body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
                Observable.zip(
                    Observable.just(a), roomRepository.sendParteDiario(body), { _, mensaje ->
                        mensaje
                    })
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Mensaje> {

                override fun onSubscribe(d: Disposable) {
                    Log.i("TAG", d.toString())
                }

                override fun onNext(m: Mensaje) {
                    Log.i("TAG", "RECIBIENDO LOS DATOS")
                    updateParteDiario(m)
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
                    mensajeSuccess.value = "Datos enviados"
                }
            })
    }

    private fun updateParteDiario(m: Mensaje) {
        roomRepository.updateParteDiario(m)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }


    fun sendInspeccion(context: Context) {
        val ot: Observable<List<InspeccionPhoto>> = roomRepository.getInspeccionesPhotoTask()
        ot.flatMap { observable ->
            Observable.fromIterable(observable).flatMap { a ->
                val b = MultipartBody.Builder()
                if (a.fotoUrl.isNotEmpty()) {
                    val file = File(Util.getFolder(context), a.fotoUrl)
                    if (file.exists()) {
                        b.addFormDataPart(
                            "files", file.name,
                            RequestBody.create(
                                MediaType.parse("multipart/form-data"), file
                            )
                        )
                    }
                }
                b.setType(MultipartBody.FORM)
                val body = b.build()
                Observable.zip(
                    Observable.just(a), roomRepository.sendInspeccionPhotos(body), { _, mensaje ->
                        mensaje
                    })
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {

                override fun onSubscribe(d: Disposable) {
                    Log.i("TAG", d.toString())
                }

                override fun onNext(m: String) {
                    Log.i("TAG", m)
//                    updateParteDiario(m)
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
                    sendInspeccionesTask()
                }
            })
    }

    private fun sendInspeccionesTask() {
        val ot: Observable<List<InspeccionPoste>> = roomRepository.getInspeccionesTask()
        ot.flatMap { observable ->
            Observable.fromIterable(observable).flatMap { a ->
                val json = Gson().toJson(a)
                Log.i("TAG", json)
                val body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
                Observable.zip(
                    Observable.just(a), roomRepository.sendInspecciones(body), { _, mensaje ->
                        mensaje
                    })
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Mensaje> {

                override fun onSubscribe(d: Disposable) {
                    Log.i("TAG", d.toString())
                }

                override fun onNext(m: Mensaje) {
                    Log.i("TAG", "RECIBIENDO LOS DATOS")
                    updateInspeccion(m)
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
                    mensajeSuccess.value = "Datos enviados"
                }
            })
    }

    private fun updateInspeccion(m: Mensaje) {
        roomRepository.updateInspeccion(m)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {
                    mensajeError.value = e.message
                }
            })
    }
}