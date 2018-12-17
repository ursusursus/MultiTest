package com.ursus.myapplication

import android.app.Application
import com.ursus.core.di.AppComponentProvider

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class App : Application(), AppComponentProvider {

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        _appComponent = AppComponent(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> appComponent(): T {
        return _appComponent as T
    }
}