package com.ursus.sharedlib1.di

import com.ursus.sharedlib1.FooManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Module
object FooModule {

    @Singleton @Provides @JvmStatic
    fun fooManager() = FooManager()

}