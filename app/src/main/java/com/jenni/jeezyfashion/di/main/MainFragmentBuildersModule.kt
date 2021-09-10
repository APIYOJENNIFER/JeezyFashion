package com.jenni.jeezyfashion.di.main

import com.jenni.jeezyfashion.ui.dashboard.DashboardFragment
import com.jenni.jeezyfashion.ui.dresses.DressesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment?

    @ContributesAndroidInjector
    abstract fun contributeDressesFragment(): DressesFragment?
}