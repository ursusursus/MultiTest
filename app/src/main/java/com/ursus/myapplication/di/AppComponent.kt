package com.ursus.myapplication.di

import com.ursus.core.di.ContextModule
import com.ursus.core.di.CoreModule
import com.ursus.feature2.di.BarModule
import com.ursus.myapplication.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        CoreModule::class,
        com.ursus.sharedlib1.di.AppModule::class,
        ContextModule::class,
        BarModule::class
    ]
)
interface AppComponent :
    com.ursus.core.di.AppComponent,
    com.ursus.sharedlib1.di.AppComponent {

    fun inject(activity: MainActivity)
}