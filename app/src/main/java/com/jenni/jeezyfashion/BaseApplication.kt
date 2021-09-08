package com.jenni.jeezyfashion

import com.jenni.jeezyfashion.di.AppComponent
import com.jenni.jeezyfashion.di.DaggerAppComponent
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AppComponent? {
        return DaggerAppComponent.builder().application(this)!!.build()
    }
}