package com.dsige.appapplus.data.module

import com.dsige.appapplus.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

abstract class FragmentBindingModule {

    @Module
    abstract class Main {
        @ContributesAndroidInjector
        internal abstract fun providMainFragment(): MainFragment
    }


    @Module
    abstract class Form {
        @ContributesAndroidInjector
        internal abstract fun providOneMTFragment(): MT1Fragment

        @ContributesAndroidInjector
        internal abstract fun providTwoMTFragment(): MT2Fragment

        @ContributesAndroidInjector
        internal abstract fun providBT1Fragment(): BT1Fragment

        @ContributesAndroidInjector
        internal abstract fun providBT2Fragment(): BT2Fragment
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