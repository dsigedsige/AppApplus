package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.PuestoTierra

@Dao
interface PuestoTierraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPuestoTierraTask(c: PuestoTierra)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPuestoTierraListTask(c: List<PuestoTierra>)

    @Update
    fun updatePuestoTierraTask(vararg c: PuestoTierra)

    @Delete
    fun deletePuestoTierraTask(c: PuestoTierra)

    @Query("SELECT * FROM PuestoTierra")
    fun getPuestoTierra(): List<PuestoTierra>

    @Query("DELETE FROM PuestoTierra")
    fun deleteAll()

    @Query("SELECT * FROM PuestoTierra")
    fun getPuestoTierras(): LiveData<List<PuestoTierra>>
}