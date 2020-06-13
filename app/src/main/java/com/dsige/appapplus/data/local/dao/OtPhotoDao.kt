package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtPhoto

@Dao
interface OtPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOtPhotoTask(c: OtPhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOtPhotoListTask(c: List<OtPhoto>)

    @Update
    fun updateOtPhotoTask(vararg c: OtPhoto)

    @Delete
    fun deleteOtPhotoTask(c: OtPhoto)

    @Query("SELECT * FROM OtPhoto WHERE formatoId =:id")
    fun getOtPhotosById(id : Int): LiveData<List<OtPhoto>>

    @Query("DELETE FROM OtPhoto")
    fun deleteAll()

    @Query("SELECT * FROM OtPhoto WHERE formatoId =:id ")
    fun getPhotoFkId(id: Int): List<OtPhoto>

    @Query("SELECT * FROM OtPhoto WHERE formatoId =:id ")
    fun getPhotoById(id: Int): LiveData<List<OtPhoto>>
}