package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.InspeccionCable

@Dao
interface InspeccionCableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionCableTask(c: InspeccionCable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionCableListTask(c: List<InspeccionCable>)

    @Update
    fun updateInspeccionCableTask(vararg c: InspeccionCable)

    @Delete
    fun deleteInspeccionCableTask(c: InspeccionCable)

    @Query("SELECT * FROM InspeccionCable")
    fun getInspeccionCable(): LiveData<List<InspeccionCable>>

    @Query("DELETE FROM InspeccionCable")
    fun deleteAll()

    @Query("SELECT * FROM InspeccionCable WHERE inspeccionCampoId =:id")
    fun getInspeccionCableById(id: Int): LiveData<InspeccionCable>

    @Query("SELECT * FROM InspeccionCable WHERE inspeccionCampoId =:id")
    fun getInspeccionByIdTask(id: Int): InspeccionCable

    @Query("UPDATE InspeccionCable SET identity=:codigoRetornoCable WHERE  inspeccionCampoId =:codigoBase")
    fun updateEnabledCable(codigoBase: Int, codigoRetornoCable: Int)

}