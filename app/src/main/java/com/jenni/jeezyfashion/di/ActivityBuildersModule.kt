package com.jenni.jeezyfashion.di

import com.jenni.jeezyfashion.di.login.LoginModule
import com.jenni.jeezyfashion.di.login.LoginViewModelsModule
import com.jenni.jeezyfashion.di.main.MainFragmentBuildersModule
import com.jenni.jeezyfashion.di.main.MainViewModelsModule
import com.jenni.jeezyfashion.ui.login.LoginActivity
import com.jenni.jeezyfashion.ui.login.OTPActivity
import com.jenni.jeezyfashion.ui.main.MainActivity
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

    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            MainViewModelsModule::class,
        ]
    )
    abstract fun contributeMainActivity(): MainActivity?

    @ContributesAndroidInjector(
        modules = [
            LoginViewModelsModule::class,
            LoginModule::class,
        ]
    )
    abstract fun contributeOTPActivity(): OTPActivity?
}