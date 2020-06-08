package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtDetalle

@Dao
interface OtDetalleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegistroDetalleTask(c: OtDetalle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegistroDetalleListTask(c: List<OtDetalle>)

    @Update
    fun updateRegistroDetalleTask(vararg c: OtDetalle)

    @Delete
    fun deleteRegistroDetalleTask(c: OtDetalle)

    @Query("SELECT * FROM OtDetalle")
    fun getRegistroDetalle(): LiveData<OtDetalle>

    @Query("SELECT * FROM OtDetalle WHERE formatoDetalleId =:id")
    fun getRegistroDetalleById(id: Int): LiveData<OtDetalle>

    @Query("DELETE FROM OtDetalle")
    fun deleteAll()

    @Query("SELECT * FROM OtDetalle")
    fun getOtDetalleByTipoPaging(): DataSource.Factory<Int, OtDetalle>

    @Query("SELECT * FROM OtDetalle WHERE formatoId =:e")
    fun getOtDetalleByTipoPaging(e: Int): DataSource.Factory<Int, OtDetalle>

    @Query("SELECT * FROM OtDetalle WHERE formatoId =:id")
    fun getOtDetalleById(id: Int): LiveData<List<OtDetalle>>

    @Query("SELECT * FROM OtDetalle WHERE formatoDetalleId =:id")
    fun getFormById(id: Int): LiveData<OtDetalle>

    @Query("SELECT formatoDetalleId FROM OtDetalle ORDER BY formatoDetalleId DESC LIMIT 1")
    fun getMaxIdOtDetalle(): LiveData<Int>

    @Query("SELECT * FROM OtDetalle WHERE formatoDetalleId =:id ")
    fun getOtDetalleVerificate(id: Int): OtDetalle

    @Query("SELECT * FROM OtDetalle WHERE formatoId =:id ")
    fun getDetalleFkId(id: Int): List<OtDetalle>
}