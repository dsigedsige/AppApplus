package com.dsige.appapplus.data.local.repository

import com.dsige.appapplus.data.local.model.Sync
import com.dsige.appapplus.data.local.model.Usuario
import com.dsige.appapplus.helper.Mensaje
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Cache-Control: no-cache")
    @POST("Login")
    fun getLogin(@Body body: RequestBody): Observable<Usuario>

    @Headers("Cache-Control: no-cache")
    @GET("Sync")
    fun getSync(@Query("id") id: Int, @Query("version") version: String): Observable<Sync>

    @Headers("Cache-Control: no-cache")
    @POST("SaveTrabajo")
    fun saveTrabajo(@Body body: RequestBody): Observable<Mensaje>

    @Headers("Cache-Control: no-cache")
    @POST("UpdateOt")
    fun updateOt(@Body body: RequestBody): Observable<Mensaje>

    @Headers("Cache-Control: no-cache")
    @POST("SaveParteDiario")
    fun sendParteDiario(@Body body: RequestBody): Observable<Mensaje>
}