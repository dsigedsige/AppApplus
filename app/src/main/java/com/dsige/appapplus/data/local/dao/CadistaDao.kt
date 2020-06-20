package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.Cadista

@Dao
interface CadistaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCadistaTask(c: Cadista)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCadistaListTask(c: List<Cadista>)

    @Update
    fun updateCadistaTask(vararg c: Cadista)

    @Delete
    fun deleteCadistaTask(c: Cadista)

    @Query("SELECT * FROM Cadista")
    fun getCadista(): List<Cadista>

    @Query("DELETE FROM Cadista")
    fun deleteAll()

    @Query("SELECT * FROM Cadista")
    fun getCadistas(): LiveData<List<Cadista>>
}