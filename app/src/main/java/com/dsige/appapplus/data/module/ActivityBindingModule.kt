package com.dsige.appapplus.data.module

import com.dsige.appapplus.ui.activities.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Main::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Camera::class])
    internal abstract fun bindCameraActivity(): CameraActivity

    @ContributesAndroidInjector
    internal abstract fun bindFormFormMainActivity(): FormMainActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Form::class])
    internal abstract fun bindFormRevelamientoActivity(): FormSecondActivity

    @ContributesAndroidInjector
    internal abstract fun bindFormatoActivity(): FormatoActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Equipo::class])
    internal abstract fun bindEquipoMainActivity(): EquipoMainActivity

    @ContributesAndroidInjector
    internal abstract fun bindEquipoSecondActivity(): EquipoSecondActivity

    @ContributesAndroidInjector
    internal abstract fun bindPreviewCameraActivity(): PreviewCameraActivity
}