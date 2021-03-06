package com.jenni.jeezyfashion.di;

import androidx.lifecycle.ViewModelProvider;

import com.jenni.jeezyfashion.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
