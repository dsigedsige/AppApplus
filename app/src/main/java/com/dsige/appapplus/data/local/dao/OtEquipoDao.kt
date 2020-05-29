package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtEquipo

@Dao
interface OtEquipoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegistroDetalleTask(c: OtEquipo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegistroDetalleListTask(c: List<OtEquipo>)

    @Update
    fun updateRegistroDetalleTask(vararg c: OtEquipo)

    @Delete
    fun deleteRegistroDetalleTask(c: OtEquipo)

    @Query("SELECT * FROM OtEquipo")
    fun getRegistroDetalle(): LiveData<OtEquipo>

    @Query("SELECT * FROM OtEquipo WHERE formatoId =:id")
    fun getRegistroDetalleById(id: Int): LiveData<OtEquipo>

    @Query("DELETE FROM OtEquipo")
    fun deleteAll()

    @Query("SELECT * FROM OtEquipo")
    fun getOtEquipoByTipoPaging(): DataSource.Factory<Int, OtEquipo>

    @Query("SELECT * FROM OtEquipo WHERE formatoId =:e")
    fun getOtEquipoByTipoPaging(e: Int): DataSource.Factory<Int, OtEquipo>

    @Query("SELECT * FROM OtEquipo WHERE formatoId =:id")
    fun getOtEquipoById(id: Int): LiveData<List<OtEquipo>>

    @Query("SELECT * FROM OtEquipo WHERE formatoId =:id")
    fun getFormById(id: Int): LiveData<OtEquipo>

    @Query("SELECT * FROM OtEquipo WHERE tipoEquipo =:tipo AND formatoId =:id")
    fun getEquipoByTipo(tipo: Int, id: Int): LiveData<List<OtEquipo>>

    @Query("SELECT * FROM OtEquipo WHERE equipoId =:id")
    fun getEquipoById(id: Int): LiveData<OtEquipo>
}