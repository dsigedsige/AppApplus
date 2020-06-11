package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.Grupo

@Dao
interface GrupoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGrupoTask(c: Grupo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGrupoListTask(c: List<Grupo>)

    @Update
    fun updateGrupoTask(vararg c: Grupo)

    @Delete
    fun deleteGrupoTask(c: Grupo)

    @Query("SELECT * FROM Grupo")
    fun getGrupo(): LiveData<Grupo>

    @Query("SELECT * FROM Grupo WHERE detalleId =:id")
    fun getGrupoById(id: Int): LiveData<List<Grupo>>

    @Query("DELETE FROM Grupo")
    fun deleteAll()
}