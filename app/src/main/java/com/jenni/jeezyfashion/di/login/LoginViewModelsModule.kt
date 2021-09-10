package com.jenni.jeezyfashion.di.login

import dagger.Binds
import dagger.multibindings.IntoMap
import com.jenni.jeezyfashion.di.ViewModelKey
import com.jenni.jeezyfashion.ui.login.LoginViewModel
import androidx.lifecycle.ViewModel
import dagger.Module

@Module
abstract class LoginViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel?): ViewModel?
}