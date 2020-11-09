package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.InspeccionPoste

@Dao
interface InspeccionPosteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionPosteTask(c: InspeccionPoste)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionPosteListTask(c: List<InspeccionPoste>)

    @Update
    fun updateInspeccionPosteTask(vararg c: InspeccionPoste)

    @Delete
    fun deleteInspeccionPosteTask(c: InspeccionPoste)

    @Query("SELECT * FROM InspeccionPoste")
    fun getInspeccionPoste(): LiveData<List<InspeccionPoste>>

    @Query("SELECT * FROM InspeccionPoste WHERE estadoId =:e")
    fun getInspeccionPoste(e: Int): LiveData<List<InspeccionPoste>>

    @Query("DELETE FROM InspeccionPoste")
    fun deleteAll()

    @Query("SELECT * FROM InspeccionPoste WHERE inspeccionCampoId =:id")
    fun getInspeccionById(id: Int): LiveData<InspeccionPoste>
}