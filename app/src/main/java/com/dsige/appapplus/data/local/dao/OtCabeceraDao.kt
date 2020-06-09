package com.dsige.appapplus.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dsige.appapplus.data.local.model.OtCabecera

@Dao
interface OtCabeceraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegistroTask(c: OtCabecera)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegistroListTask(c: List<OtCabecera>)

    @Update
    fun updateRegistroTask(vararg c: OtCabecera)

    @Delete
    fun deleteRegistroTask(c: OtCabecera)

    @Query("SELECT * FROM OtCabecera")
    fun getRegistro(): LiveData<OtCabecera>

    @Query("SELECT * FROM OtCabecera WHERE otId =:id ")
    fun getOtCabeceraByTipoPaging(id:Int): DataSource.Factory<Int, OtCabecera>

    @Query("SELECT * FROM OtCabecera WHERE otId =:id AND formatoId =:e")
    fun getOtCabeceraByTipoPaging(id:Int,e:Int): DataSource.Factory<Int, OtCabecera>

    @Query("SELECT * FROM OtCabecera WHERE formatoId =:id")
    fun getRegistroById(id: Int): LiveData<OtCabecera>

    @Query("DELETE FROM OtCabecera")
    fun deleteAll()

    @Query("SELECT * FROM OtCabecera WHERE formatoId =:id")
    fun getOtCabeceraById(id: Int): LiveData<OtCabecera>

    @Query("SELECT formatoId FROM OtCabecera ORDER BY formatoId DESC LIMIT 1")
    fun getMaxIdOt(): LiveData<Int>

    @Query("SELECT * FROM OtCabecera WHERE otId =:id ")
    fun getOtCabeceraByIdTask(id: Int): OtCabecera

    @Query("SELECT * FROM OtCabecera WHERE active =:i ")
    fun getOtCabeceraActive(i: Int): List<OtCabecera>

    @Query("UPDATE OtCabecera SET identity =:codigoRetorno , active = 0 WHERE formatoId =:codigoBase ")
    fun updateCabecera(codigoBase: Int, codigoRetorno: Int)

}