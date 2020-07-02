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
    internal abstract fun bindFormatoActivity(): FormatoActivity

    @ContributesAndroidInjector
    internal abstract fun bindFormFormMainActivity(): FormMainActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Form::class])
    internal abstract fun bindFormRevelamientoActivity(): FormSecondActivity

    @ContributesAndroidInjector
    internal abstract fun bindProtocoloMainActivity(): ProtocoloMainActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Protocolo::class])
    internal abstract fun bindProtocoloActivity(): ProtocoloActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Equipo::class])
    internal abstract fun bindEquipoMainActivity(): EquipoMainActivity

    @ContributesAndroidInjector
    internal abstract fun bindEquipoSecondActivity(): EquipoSecondActivity

    @ContributesAndroidInjector
    internal abstract fun bindPreviewCameraActivity(): PreviewCameraActivity

    @ContributesAndroidInjector
    internal abstract fun bindMainHojaActivity(): HojaMainActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Hoja::class])
    internal abstract fun bindHojaActivity(): HojaActivity

    @ContributesAndroidInjector
    internal abstract fun bindHoja123Activity(): Hoja123Activity

    @ContributesAndroidInjector
    internal abstract fun bindHoja4Activity(): Hoja4Activity

    @ContributesAndroidInjector
    internal abstract fun bindHoja56Activity(): Hoja56Activity

    @ContributesAndroidInjector
    internal abstract fun bindLevantamientoActivity(): LevantamientoMainActivity

    @ContributesAndroidInjector
    internal abstract fun bindPhotoActivity(): PhotoActivity

    @ContributesAndroidInjector
    internal abstract fun bindParteDiarioActivity(): ParteDiarioActivity
}