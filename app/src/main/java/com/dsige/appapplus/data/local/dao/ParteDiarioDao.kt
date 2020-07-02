package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.ParteDiario

@Dao
interface ParteDiarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParteDiarioTask(c: ParteDiario)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParteDiarioListTask(c: List<ParteDiario>)

    @Update
    fun updateParteDiarioTask(vararg c: ParteDiario)

    @Delete
    fun deleteParteDiarioTask(c: ParteDiario)

    @Query("SELECT * FROM ParteDiario")
    fun getParteDiario(): List<ParteDiario>

    @Query("DELETE FROM ParteDiario")
    fun deleteAll()

    @Query("SELECT * FROM ParteDiario")
    fun getParteDiarios(): LiveData<List<ParteDiario>>

    @Query("SELECT * FROM ParteDiario WHERE otId =:id")
    fun getParteDiarioByOt(id: Int): LiveData<ParteDiario>

    @Query("SELECT * FROM ParteDiario WHERE estado =:i")
    fun getPrteDiarioTask(i: Int): List<ParteDiario>

    @Query("UPDATE ParteDiario SET active = 2 , identity =:r  WHERE parteDiarioId =:id")
    fun updateEnabledParteDiario(id: Int,r:Int)
}