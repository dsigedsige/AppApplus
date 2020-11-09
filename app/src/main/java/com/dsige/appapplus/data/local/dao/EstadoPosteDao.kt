package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.EstadoPoste

@Dao
interface EstadoPosteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstadoTask(c: EstadoPoste)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstadoListTask(c: List<EstadoPoste>)

    @Update
    fun updateEstadoTask(vararg c: EstadoPoste)

    @Delete
    fun deleteEstadoTask(c: EstadoPoste)

    @Query("SELECT * FROM EstadoPoste")
    fun getEstado(): List<EstadoPoste>

    @Query("DELETE FROM EstadoPoste")
    fun deleteAll()

    @Query("SELECT * FROM EstadoPoste ORDER BY orden ASC")
    fun getEstados(): LiveData<List<EstadoPoste>>
}