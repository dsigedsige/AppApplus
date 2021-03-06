package com.dsige.appapplus.data.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.data.viewModel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsuarioViewModel::class)
    internal abstract fun bindUserViewModel(usuarioViewModel: UsuarioViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistroViewModel::class)
    internal abstract fun bindRegistroViewModel(registroViewModel: RegistroViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EquipoViewModel::class)
    internal abstract fun bindEquipoViewModel(equipoViewModel: EquipoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProtocoloViewModel::class)
    internal abstract fun bindProtocoloViewModel(protocoloViewModel: ProtocoloViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HojaViewModel::class)
    internal abstract fun bindHojaViewModel(hojaViewModel: HojaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InspeccionViewModel::class)
    internal abstract fun bindInspeccioonViewModel(inspeccionViewModel: InspeccionViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}