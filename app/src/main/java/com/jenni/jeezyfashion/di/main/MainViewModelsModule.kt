package com.jenni.jeezyfashion.di.main

import androidx.lifecycle.ViewModel
import com.jenni.jeezyfashion.di.ViewModelKey
import com.jenni.jeezyfashion.ui.dashboard.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel?): ViewModel?

}