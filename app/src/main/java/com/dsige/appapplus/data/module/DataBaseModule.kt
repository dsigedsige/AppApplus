package com.dsige.appapplus.data.module

import android.app.Application
import androidx.room.Room
import com.dsige.appapplus.data.local.AppDataBase
import com.dsige.appapplus.data.local.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    internal fun provideRoomDatabase(application: Application): AppDataBase {
        if (AppDataBase.INSTANCE == null) {
            synchronized(AppDataBase::class.java) {
                if (AppDataBase.INSTANCE == null) {
                    AppDataBase.INSTANCE = Room.databaseBuilder(
                        application.applicationContext,
                        AppDataBase::class.java, AppDataBase.DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }
        return AppDataBase.INSTANCE!!
    }

    @Provides
    internal fun provideUsuarioDao(appDataBase: AppDataBase): UsuarioDao {
        return appDataBase.usuarioDao()
    }

    @Provides
    internal fun provideFormatoDao(appDataBase: AppDataBase): FormatoDao {
        return appDataBase.formatoDao()
    }

    @Provides
    internal fun provideGrupoDao(appDataBase: AppDataBase): GrupoDao {
        return appDataBase.grupoDao()
    }

    @Provides
    internal fun provideRegistroDao(appDataBase: AppDataBase): OtCabeceraDao {
        return appDataBase.otCabeceraDao()
    }

    @Provides
    internal fun provideRegistroDetalleDao(appDataBase: AppDataBase): OtDetalleDao {
        return appDataBase.otDetalleDao()
    }

    @Provides
    internal fun provideOtEquipoDao(appDataBase: AppDataBase): OtEquipoDao {
        return appDataBase.otEquipoDao()
    }

    @Provides
    internal fun provideOtProtocoloDao(appDataBase: AppDataBase): OtProtocoloDao {
        return appDataBase.otProtocoloDao()
    }

    @Provides
    internal fun provideOtDao(appDataBase: AppDataBase): OtDao {
        return appDataBase.otDao()
    }

    @Provides
    internal fun provideOtHoja123Dao(appDataBase: AppDataBase): OtHoja123Dao {
        return appDataBase.otHoja123Dao()
    }

    @Provides
    internal fun provideOtHoja4Dao(appDataBase: AppDataBase): OtHoja4Dao {
        return appDataBase.otHoja4Dao()
    }

    @Provides
    internal fun provideOtHoja56Dao(appDataBase: AppDataBase): OtHoja56Dao {
        return appDataBase.otHoja56Dao()
    }

    @Provides
    internal fun provideOtPhotoDao(appDataBase: AppDataBase): OtPhotoDao {
        return appDataBase.otPhotoDao()
    }

    @Provides
    internal fun provideSedDao(appDataBase: AppDataBase): SedDao {
        return appDataBase.sedDao()
    }

    @Provides
    internal fun provideEstadoTrabajoDao(appDataBase: AppDataBase): EstadoTrabajoDao {
        return appDataBase.estadoTrabajoDao()
    }

    @Provides
    internal fun provideEstadoPosteDao(appDataBase: AppDataBase): EstadoPosteDao {
        return appDataBase.estadoPosteDao()
    }

    @Provides
    internal fun provideCadistaDao(appDataBase: AppDataBase): CadistaDao {
        return appDataBase.cadistaDao()
    }

    @Provides
    internal fun providePuestoTierraDao(appDataBase: AppDataBase): PuestoTierraDao {
        return appDataBase.puestoTierraDao()
    }

    @Provides
    internal fun provideParteDiarioDao(appDataBase: AppDataBase): ParteDiarioDao {
        return appDataBase.parteDiarioDao()
    }

    @Provides
    internal fun provideSupervisorDao(appDataBase: AppDataBase): SupervisorDao {
        return appDataBase.supervisorDao()
    }

    @Provides
    internal fun provideInspeccioPosteDao(appDataBase: AppDataBase): InspeccionPosteDao {
        return appDataBase.inspeccionPosteDao()
    }
}