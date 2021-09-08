package com.jenni.jeezyfashion.di

import com.jenni.jeezyfashion.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity?
}