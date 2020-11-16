package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.InspeccionEquipo

@Dao
interface InspeccionEquipoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionEquipoTask(c: InspeccionEquipo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionEquipoListTask(c: List<InspeccionEquipo>)

    @Update
    fun updateInspeccionEquipoTask(vararg c: InspeccionEquipo)

    @Delete
    fun deleteInspeccionEquipoTask(c: InspeccionEquipo)

    @Query("SELECT * FROM InspeccionEquipo")
    fun getInspeccionEquipo(): LiveData<List<InspeccionEquipo>>

    @Query("DELETE FROM InspeccionEquipo")
    fun deleteAll()

    @Query("SELECT * FROM InspeccionEquipo WHERE inspeccionCampoId =:id")
    fun getInspeccionEquipoById(id: Int): LiveData<InspeccionEquipo>

    @Query("SELECT * FROM InspeccionEquipo WHERE inspeccionCampoId =:id")
    fun getInspeccionByIdTask(id: Int): InspeccionEquipo

    @Query("UPDATE InspeccionEquipo SET identity =:codigoRetornoEquipo WHERE inspeccionCampoId =:codigoBase")
    fun updateEnabledEquipo(codigoBase: Int, codigoRetornoEquipo: Int)
}