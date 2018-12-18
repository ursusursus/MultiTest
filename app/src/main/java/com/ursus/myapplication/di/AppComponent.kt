package com.ursus.myapplication.di

import android.app.Activity
import android.app.Service
import androidx.fragment.app.Fragment
import com.ursus.core.di.ContextModule
import com.ursus.core.di.CoreModule
import com.ursus.feature2.di.BarModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.DispatchingAndroidInjector
import javax.inject.Singleton

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        MyApplicationModule::class,
        AppModule::class,
        CoreModule::class,
        com.ursus.sharedlib1.di.AppModule::class,
        ContextModule::class,
        BarModule::class
    ]
)
interface AppComponent {
    val activityInjector: DispatchingAndroidInjector<Activity>
    val serviceInjector: DispatchingAndroidInjector<Service>
    val fragmentInjector: DispatchingAndroidInjector<Fragment>


}