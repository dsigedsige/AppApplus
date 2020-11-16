package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.InspeccionPhoto

@Dao
interface InspeccionPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionPhotoTask(c: InspeccionPhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInspeccionPhotoListTask(c: List<InspeccionPhoto>)

    @Update
    fun updateInspeccionPhotoTask(vararg c: InspeccionPhoto)

    @Delete
    fun deleteInspeccionPhotoTask(c: InspeccionPhoto)

    @Query("SELECT * FROM InspeccionPhoto")
    fun getInspeccionPhoto(): LiveData<List<InspeccionPhoto>>

    @Query("DELETE FROM InspeccionPhoto")
    fun deleteAll()

    @Query("SELECT * FROM InspeccionPhoto WHERE inspeccionCampoId =:id")
    fun getInspeccionPhotoById(id: Int): LiveData<List<InspeccionPhoto>>

    @Query("SELECT * FROM InspeccionPhoto WHERE inspeccionCampoId =:id")
    fun getInspeccionPhotoByIdTask(id: Int): List<InspeccionPhoto>

    @Query("SELECT * FROM InspeccionPhoto WHERE fotoUrl =:f")
    fun getInspeccionByUrlTask(f: String): InspeccionPhoto

    @Query("UPDATE InspeccionPhoto  SET active = 0 WHERE inspeccionCampoId =:codigoBase")
    fun updateEnabledPhoto(codigoBase: Int)

}