package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtHoja4

@Dao
interface OtHoja4Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoja4Task(c: OtHoja4)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoja4ListTask(c: List<OtHoja4>)

    @Update
    fun updateHoja4Task(vararg c: OtHoja4)

    @Delete
    fun deleteHoja4Task(c: OtHoja4)

    @Query("SELECT * FROM OtHoja4")
    fun getHoja4(): LiveData<OtHoja4>

    @Query("SELECT * FROM OtHoja4 WHERE hoja4Id =:id")
    fun getHoja4ById(id: Int): LiveData<OtHoja4>

    @Query("DELETE FROM OtHoja4")
    fun deleteAll()

    @Query("SELECT * FROM OtHoja4")
    fun getOtHoja4ByTipoPaging(): DataSource.Factory<Int, OtHoja4>

    @Query("SELECT * FROM OtHoja4 WHERE formatoId =:e")
    fun getOtHoja4ByTipoPaging(e: Int): DataSource.Factory<Int, OtHoja4>

    @Query("SELECT * FROM OtHoja4 WHERE formatoId =:id")
    fun getOtHoja4ById(id: Int): LiveData<List<OtHoja4>>

    @Query("SELECT * FROM OtHoja4 WHERE formatoId =:id")
    fun getFormById(id: Int): LiveData<OtHoja4>

    @Query("SELECT * FROM OtHoja4 WHERE formatoId =:id AND item =:t ")
    fun getHoja4ByTipo(id: Int, t: Int): LiveData<List<OtHoja4>>
}