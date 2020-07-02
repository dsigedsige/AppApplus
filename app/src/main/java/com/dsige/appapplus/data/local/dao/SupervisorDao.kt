package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.Supervisor

@Dao
interface SupervisorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupervisorTask(c: Supervisor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupervisorListTask(c: List<Supervisor>)

    @Update
    fun updateSupervisorTask(vararg c: Supervisor)

    @Delete
    fun deleteSupervisorTask(c: Supervisor)

    @Query("SELECT * FROM Supervisor")
    fun getSupervisor(): LiveData<List<Supervisor>>

    @Query("DELETE FROM Supervisor")
    fun deleteAll()

    @Query("SELECT * FROM Supervisor")
    fun getSupervisors(): LiveData<List<Supervisor>>
}