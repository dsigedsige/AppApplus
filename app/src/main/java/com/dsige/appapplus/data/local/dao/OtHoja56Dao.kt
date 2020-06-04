package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtHoja56

@Dao
interface OtHoja56Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoja56Task(c: OtHoja56)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoja56ListTask(c: List<OtHoja56>)

    @Update
    fun updateHoja56Task(vararg c: OtHoja56)

    @Delete
    fun deleteHoja56Task(c: OtHoja56)

    @Query("SELECT * FROM OtHoja56")
    fun getHoja56(): LiveData<OtHoja56>

    @Query("SELECT * FROM OtHoja56 WHERE hoja56Id =:id")
    fun getHoja56ById(id: Int): LiveData<OtHoja56>

    @Query("DELETE FROM OtHoja56")
    fun deleteAll()

    @Query("SELECT * FROM OtHoja56")
    fun getHoja56ByTipoPaging(): DataSource.Factory<Int, OtHoja56>

    @Query("SELECT * FROM OtHoja56 WHERE formatoId =:e")
    fun getHoja56ByTipoPaging(e: Int): DataSource.Factory<Int, OtHoja56>

    @Query("SELECT * FROM OtHoja56 WHERE formatoId =:id")
    fun getFormById(id: Int): LiveData<OtHoja56>

    @Query("SELECT * FROM OtHoja56 WHERE formatoId =:id AND item =:t ")
    fun getHoja56ByTipo(id: Int, t: Int): LiveData<List<OtHoja56>>
}