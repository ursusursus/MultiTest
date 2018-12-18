package com.ursus.myapplication.di

import com.ursus.myapplication.Bar
import dagger.Module
import dagger.Provides

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Module
object AppModule {
    @Provides @JvmStatic
    fun bar(): Bar = Bar(1)
}