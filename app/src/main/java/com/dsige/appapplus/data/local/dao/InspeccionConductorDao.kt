package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.InspeccionConductor

@Dao
interface InspeccionConductorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionConductorTask(c: InspeccionConductor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionConductorListTask(c: List<InspeccionConductor>)

    @Update
    fun updateInspeccionConductorTask(vararg c: InspeccionConductor)

    @Delete
    fun deleteInspeccionConductorTask(c: InspeccionConductor)

    @Query("SELECT * FROM InspeccionConductor")
    fun getInspeccionConductor(): LiveData<List<InspeccionConductor>>

    @Query("DELETE FROM InspeccionConductor")
    fun deleteAll()

    @Query("SELECT * FROM InspeccionConductor WHERE inspeccionCampoId =:id")
    fun getInspeccionConductorById(id: Int): LiveData<InspeccionConductor>

    @Query("SELECT * FROM InspeccionConductor WHERE inspeccionCampoId =:id")
    fun getInspeccionByIdTask(id: Int): InspeccionConductor

    @Query("UPDATE InspeccionConductor SET identity=:codigoRetornoConductor WHERE inspeccionCampoId =:codigoBase")
    fun updateEnabledConductor(codigoBase: Int, codigoRetornoConductor: Int)

}