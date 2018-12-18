package com.ursus.myapplication

import android.app.Application
import com.ursus.core.di.ContextModule
import com.ursus.myapplication.di.AppComponent
import com.ursus.myapplication.di.DaggerAppComponent
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class App : Application(), HasActivityInjector, HasServiceInjector, HasSupportFragmentInjector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun activityInjector() = appComponent.activityInjector
    override fun serviceInjector() = appComponent.serviceInjector
    override fun supportFragmentInjector() = appComponent.fragmentInjector
}