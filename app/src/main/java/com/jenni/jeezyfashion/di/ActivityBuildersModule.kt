package com.jenni.jeezyfashion.di

import com.jenni.jeezyfashion.di.login.LoginModule
import com.jenni.jeezyfashion.di.login.LoginViewModelsModule
import com.jenni.jeezyfashion.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
        modules = [
            LoginViewModelsModule::class,
            LoginModule::class,
        ]
    )
    abstract fun contributeLoginActivity(): LoginActivity?
}