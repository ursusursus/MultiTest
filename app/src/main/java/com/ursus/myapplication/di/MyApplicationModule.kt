package com.ursus.myapplication.di

import com.ursus.core.CallService
import com.ursus.feature1.BarFragment
import com.ursus.myapplication.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 18.12.2018.
 */
@Module
abstract class MyApplicationModule {
    @ContributesAndroidInjector abstract fun contributeActivityInjector(): MainActivity
    @ContributesAndroidInjector abstract fun contributesServiceInjector(): CallService
    @ContributesAndroidInjector abstract fun contributesServiceInjector2(): BarFragment
}