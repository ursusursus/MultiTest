package com.ursus.myapplication.di

import android.app.Application
import com.ursus.core.di.CoreModule
import com.ursus.feature2.di.QuaxModule
import com.ursus.myapplication.App
import com.ursus.sharedlib1.di.FooModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BarModule::class,
        CoreModule::class,
        FooModule::class,
        QuaxModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }
}