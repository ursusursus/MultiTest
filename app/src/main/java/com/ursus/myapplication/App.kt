package com.ursus.myapplication

import android.app.Application
import com.ursus.core.di.AppComponentProvider
import com.ursus.core.di.ContextModule
import com.ursus.myapplication.di.AppComponent
import com.ursus.myapplication.di.DaggerAppComponent

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class App : Application(), AppComponentProvider {

    private val appComponent by lazy<AppComponent> {
        DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> appComponent(): T {
        return appComponent as T
    }
}