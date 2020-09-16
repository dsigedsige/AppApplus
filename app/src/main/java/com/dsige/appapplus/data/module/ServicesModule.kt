package com.dsige.appapplus.data.module

import com.dsige.appapplus.ui.broadcasts.*
import com.dsige.appapplus.ui.services.SocketServices
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServicesModule {

    @ContributesAndroidInjector
    internal abstract fun provideMainReceiver(): MovilReceiver

    @ContributesAndroidInjector
    internal abstract fun provideGpsReceiver(): GpsReceiver

    @ContributesAndroidInjector
    internal abstract fun provideSocketReceiver(): SocketServices

}