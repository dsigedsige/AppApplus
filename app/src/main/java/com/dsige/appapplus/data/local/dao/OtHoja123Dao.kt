package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtHoja123

@Dao
interface OtHoja123Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoja123Task(c: OtHoja123)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHoja123ListTask(c: List<OtHoja123>)

    @Update
    fun updateHoja123Task(vararg c: OtHoja123)

    @Delete
    fun deleteHoja123Task(c: OtHoja123)

    @Query("SELECT * FROM OtHoja123")
    fun getHoja123(): LiveData<OtHoja123>

    @Query("DELETE FROM OtHoja123")
    fun deleteAll()

    @Query("SELECT * FROM OtHoja123 WHERE formatoId =:id")
    fun getFormById(id: Int): LiveData<OtHoja123>

    @Query("SELECT * FROM OtHoja123 WHERE formatoId =:id AND item =:t ")
    fun getHoja123ByTipo(id: Int, t: Int): LiveData<List<OtHoja123>>

    @Query("SELECT * FROM OtHoja123 WHERE hoja123Id =:id")
    fun getHoja123ById(id: Int): LiveData<OtHoja123>

    @Query("SELECT * FROM OtHoja123 WHERE formatoId =:id")
    fun getHoja123FkId(id: Int): List<OtHoja123>

    @Query("SELECT * FROM OtHoja123 WHERE formatoId =:id AND item =:t ")
    fun getHoja123ByItem(id: Int, t: Int): LiveData<OtHoja123>
}