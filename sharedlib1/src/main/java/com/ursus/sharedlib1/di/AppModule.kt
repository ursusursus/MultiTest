package com.ursus.sharedlib1.di

import com.ursus.sharedlib1.FooManager
import dagger.Module
import dagger.Provides

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Module
class AppModule {
    @Provides fun fooManager() = FooManager()

}