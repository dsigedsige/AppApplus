package com.dsige.appapplus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dsige.appapplus.data.local.dao.*
import com.dsige.appapplus.data.local.model.*

@Database(
    entities = [
        Usuario::class,
        Grupo::class,
        Formato::class,
        OtCabecera::class,
        OtDetalle::class,
        OtEquipo::class,
        Ot::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun grupoDao(): GrupoDao
    abstract fun formatoDao(): FormatoDao
    abstract fun otCabeceraDao(): OtCabeceraDao
    abstract fun otDetalleDao(): OtDetalleDao
    abstract fun otEquipoDao(): OtEquipoDao
    abstract fun otDao(): OtDao

    companion object {
        @Volatile
        var INSTANCE: AppDataBase? = null
        val DB_NAME = "applus_db"
    }

    fun getDatabase(context: Context): AppDataBase {
        if (INSTANCE == null) {
            synchronized(AppDataBase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java, "applus_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }
        return INSTANCE!!
    }
}