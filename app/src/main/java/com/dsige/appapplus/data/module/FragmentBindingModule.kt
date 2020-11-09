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

        @ContributesAndroidInjector
        internal abstract fun providInspeccionPosteFragment(): InspeccionPosteFragment
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

        @ContributesAndroidInjector
        internal abstract fun providEquipoDetalleFragment(): EquipoDetalleFragment
    }

    @Module
    abstract class Camera {
        @ContributesAndroidInjector
        internal abstract fun providCameraFragment(): CameraFragment
    }

    @Module
    abstract class Poste {
        @ContributesAndroidInjector
        internal abstract fun providPoste1Fragment(): Poste1Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste2Fragment(): Poste2Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste3Fragment(): Poste3Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste4Fragment(): Poste4Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste5Fragment(): Poste5Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste6Fragment(): Poste6Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste7Fragment(): Poste7Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste8Fragment(): Poste8Fragment
        @ContributesAndroidInjector
        internal abstract fun providPoste9Fragment(): Poste9Fragment
    }
}