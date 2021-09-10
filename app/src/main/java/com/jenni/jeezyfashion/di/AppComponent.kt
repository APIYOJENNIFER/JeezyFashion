package com.jenni.jeezyfashion.di

import android.app.Application
import dagger.android.support.AndroidSupportInjectionModule
import com.jenni.jeezyfashion.di.ActivityBuildersModule
import com.jenni.jeezyfashion.di.AppModule
import dagger.android.AndroidInjector
import com.jenni.jeezyfashion.BaseApplication
import com.jenni.jeezyfashion.SessionManager
import dagger.BindsInstance
import com.jenni.jeezyfashion.di.AppComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    AppModule::class,
    ViewModelFactoryModule::class,

])
interface AppComponent : AndroidInjector<BaseApplication?> {
    val sessionManager:SessionManager
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }
}