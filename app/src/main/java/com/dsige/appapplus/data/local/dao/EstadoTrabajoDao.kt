package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.EstadoTrabajo

@Dao
interface EstadoTrabajoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstadoTask(c: EstadoTrabajo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstadoListTask(c: List<EstadoTrabajo>)

    @Update
    fun updateEstadoTask(vararg c: EstadoTrabajo)

    @Delete
    fun deleteEstadoTask(c: EstadoTrabajo)

    @Query("SELECT * FROM EstadoTrabajo")
    fun getEstado(): List<EstadoTrabajo>

    @Query("DELETE FROM EstadoTrabajo")
    fun deleteAll()

    @Query("SELECT * FROM EstadoTrabajo ORDER BY orden ASC")
    fun getEstados(): LiveData<List<EstadoTrabajo>>
}