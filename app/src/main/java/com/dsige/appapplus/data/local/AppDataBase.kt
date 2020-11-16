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
        OtProtocolo::class,
        Ot::class,
        OtHoja123::class,
        OtHoja4::class,
        OtHoja56::class,
        Sed::class,
        EstadoTrabajo::class,
        EstadoPoste::class,
        OtPhoto::class,
        Cadista::class,
        PuestoTierra::class,
        ParteDiario::class,
        Supervisor::class,
        InspeccionPoste::class,
        InspeccionConductor::class,
        InspeccionCable::class,
        InspeccionEquipo::class,
        InspeccionPhoto::class
    ],
    version = 9,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun grupoDao(): GrupoDao
    abstract fun formatoDao(): FormatoDao
    abstract fun otCabeceraDao(): OtCabeceraDao
    abstract fun otDetalleDao(): OtDetalleDao
    abstract fun otEquipoDao(): OtEquipoDao
    abstract fun otProtocoloDao(): OtProtocoloDao
    abstract fun otDao(): OtDao
    abstract fun otHoja123Dao(): OtHoja123Dao
    abstract fun otHoja4Dao(): OtHoja4Dao
    abstract fun otHoja56Dao(): OtHoja56Dao
    abstract fun otPhotoDao(): OtPhotoDao
    abstract fun sedDao(): SedDao
    abstract fun estadoTrabajoDao(): EstadoTrabajoDao
    abstract fun estadoPosteDao(): EstadoPosteDao
    abstract fun cadistaDao(): CadistaDao
    abstract fun puestoTierraDao(): PuestoTierraDao
    abstract fun parteDiarioDao(): ParteDiarioDao
    abstract fun supervisorDao(): SupervisorDao
    abstract fun inspeccionPosteDao(): InspeccionPosteDao
    abstract fun inspeccionConductorDao(): InspeccionConductorDao
    abstract fun inspeccionCableDao(): InspeccionCableDao
    abstract fun inspeccionEquipoDao(): InspeccionEquipoDao
    abstract fun inspeccionPhotoDao(): InspeccionPhotoDao

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