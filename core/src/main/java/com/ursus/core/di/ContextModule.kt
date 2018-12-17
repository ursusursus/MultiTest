package com.ursus.core.di

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Module
class ContextModule(private val context: Context) {

    @Provides fun context() = context
}