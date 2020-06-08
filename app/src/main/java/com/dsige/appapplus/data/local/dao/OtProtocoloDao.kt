package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtProtocolo

@Dao
interface OtProtocoloDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProtocoloTask(c: OtProtocolo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProtocoloListTask(c: List<OtProtocolo>)

    @Update
    fun updateProtocoloTask(vararg c: OtProtocolo)

    @Delete
    fun deleteProtocoloTask(c: OtProtocolo)

    @Query("SELECT * FROM OtProtocolo")
    fun getProtocolo(): LiveData<OtProtocolo>

    @Query("SELECT * FROM OtProtocolo WHERE formatoId =:id")
    fun getProtocoloById(id: Int): LiveData<OtProtocolo>

    @Query("DELETE FROM OtProtocolo")
    fun deleteAll()

    @Query("SELECT * FROM OtProtocolo")
    fun getOtProtocoloByTipoPaging(): DataSource.Factory<Int, OtProtocolo>

    @Query("SELECT * FROM OtProtocolo WHERE formatoId =:e")
    fun getOtProtocoloByTipoPaging(e: Int): DataSource.Factory<Int, OtProtocolo>

    @Query("SELECT * FROM OtProtocolo WHERE formatoId =:id")
    fun getOtProtocoloById(id: Int): LiveData<List<OtProtocolo>>

    @Query("SELECT * FROM OtProtocolo WHERE formatoId =:id")
    fun getFormById(id: Int): LiveData<OtProtocolo>

    @Query("SELECT * FROM OtProtocolo WHERE formatoId =:id AND tipoProtocoloId =:t ")
    fun getProtocoloByTipo(id: Int, t: Int): LiveData<OtProtocolo>

    @Query("SELECT * FROM OtProtocolo WHERE formatoId =:id ")
    fun getProtocoloFkId(id: Int): List<OtProtocolo>

//    @Query("SELECT * FROM OtProtocolo WHERE tipoEquipo =:tipo AND formatoId =:id")
//    fun getEquipoByTipo(tipo: Int, id: Int): LiveData<List<OtProtocolo>>
//
//    @Query("SELECT * FROM OtProtocolo WHERE equipoId =:id")
//    fun getEquipoById(id: Int): LiveData<OtProtocolo>
}