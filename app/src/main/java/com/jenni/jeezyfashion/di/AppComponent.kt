package com.jenni.jeezyfashion.di

import android.app.Application
import dagger.android.support.AndroidSupportInjectionModule
import com.jenni.jeezyfashion.di.ActivityBuildersModule
import com.jenni.jeezyfashion.di.AppModule
import dagger.android.AndroidInjector
import com.jenni.jeezyfashion.BaseApplication
import dagger.BindsInstance
import com.jenni.jeezyfashion.di.AppComponent
import dagger.Component

@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, AppModule::class])
interface AppComponent : AndroidInjector<BaseApplication?> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }
}