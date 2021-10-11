package com.jenni.jeezyfashion.di.main

import com.jenni.jeezyfashion.ui.dashboard.DashboardFragment
import com.jenni.jeezyfashion.ui.dresses.DressesFragment
import com.jenni.jeezyfashion.ui.favorites.FavoritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment?

    @ContributesAndroidInjector
    abstract fun contributeDressesFragment(): DressesFragment?

    @ContributesAndroidInjector
    abstract fun contributeFavoritesFragment(): FavoritesFragment?
}