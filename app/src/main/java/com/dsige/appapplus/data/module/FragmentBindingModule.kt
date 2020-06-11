package com.dsige.appapplus.data.module

import com.dsige.appapplus.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

abstract class FragmentBindingModule {

    @Module
    abstract class Hoja {
        @ContributesAndroidInjector
        internal abstract fun providHojaFragment(): HojaFragment

        @ContributesAndroidInjector
        internal abstract fun providHojaDetalleFragment(): HojaDetalleFragment
    }

    @Module
    abstract class Main {
        @ContributesAndroidInjector
        internal abstract fun providMainFragment(): MainFragment
    }


    @Module
    abstract class Form {
        @ContributesAndroidInjector
        internal abstract fun providBMT1Fragment(): BMT1Fragment

        @ContributesAndroidInjector
        internal abstract fun providBMT2Fragment(): BMT2Fragment

        @ContributesAndroidInjector
        internal abstract fun providMBT1Fragment(): MBT1Fragment

        @ContributesAndroidInjector
        internal abstract fun providMBT2Fragment(): MBT2Fragment
    }

    @Module
    abstract class Protocolo {
        @ContributesAndroidInjector
        internal abstract fun providP1Fragment(): P1Fragment

        @ContributesAndroidInjector
        internal abstract fun providP2Fragment(): P2Fragment

        @ContributesAndroidInjector
        internal abstract fun providP3Fragment(): P3Fragment

        @ContributesAndroidInjector
        internal abstract fun providP4Fragment(): P4Fragment
    }

    @Module
    abstract class Equipo {
        @ContributesAndroidInjector
        internal abstract fun providEquipoFragment(): EquipoFragment
    }

    @Module
    abstract class Camera {
        @ContributesAndroidInjector
        internal abstract fun providCameraFragment(): CameraFragment
    }
}