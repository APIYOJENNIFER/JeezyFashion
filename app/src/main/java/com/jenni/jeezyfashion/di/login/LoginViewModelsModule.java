package com.jenni.jeezyfashion.di.login;

import androidx.lifecycle.ViewModel;

import com.jenni.jeezyfashion.di.ViewModelKey;
import com.jenni.jeezyfashion.ui.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}
