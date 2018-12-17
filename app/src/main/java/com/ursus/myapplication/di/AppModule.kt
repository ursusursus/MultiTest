package com.ursus.myapplication.di

import com.ursus.myapplication.Bar
import dagger.Module
import dagger.Provides

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Module
class AppModule {
    @Provides fun bar(): Bar = Bar(1)
}