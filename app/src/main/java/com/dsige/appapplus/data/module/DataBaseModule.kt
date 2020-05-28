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
    internal fun provideOtDao(appDataBase: AppDataBase): OtDao {
        return appDataBase.otDao()
    }
}