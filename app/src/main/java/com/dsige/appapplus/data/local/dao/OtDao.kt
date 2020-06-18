package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.Ot

@Dao
interface OtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOtTask(c: Ot)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOtListTask(c: List<Ot>)

    @Update
    fun updateOtTask(vararg c: Ot)

    @Delete
    fun deleteOtTask(c: Ot)

    @Query("SELECT * FROM Ot")
    fun getOt(): LiveData<Ot>

    @Query("SELECT * FROM Ot WHERE otId =:id")
    fun getOtById(id: Int): LiveData<Ot>

    @Query("DELETE FROM Ot")
    fun deleteAll()

    @Query("SELECT * FROM Ot")
    fun getOtByTipoPaging(): DataSource.Factory<Int, Ot>

    @Query("SELECT * FROM Ot WHERE estadoId =:id")
    fun getOtByTipoPaging(id: Int): DataSource.Factory<Int, Ot>

    @Query("SELECT * FROM Ot WHERE estadoId =:id AND nroOt LIKE :s")
    fun getOtByTipoPaging(id: Int,s:String): DataSource.Factory<Int, Ot>

    @Query("SELECT * FROM Ot WHERE nroOt LIKE :s")
    fun getOtByTipoPaging(s:String): DataSource.Factory<Int, Ot>

    @Query("SELECT * FROM Ot WHERE active =:i")
    fun getOtReasignacion(i: Int): List<Ot>

    @Query("SELECT * FROM Ot WHERE otId =:id")
    fun getOtSendById(id: Int): Ot

    @Query("UPDATE Ot SET active = 2 WHERE otId =:id")
    fun updateOtById(id: Int)
}