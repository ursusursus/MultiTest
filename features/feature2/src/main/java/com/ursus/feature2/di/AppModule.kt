package com.ursus.feature2.di

import com.ursus.feature2.Quax
import dagger.Module
import dagger.Provides

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Module
class BarModule {
    @Provides fun quax() = Quax()
}