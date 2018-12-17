package com.ursus.sharedlib1.di

import com.ursus.sharedlib1.FooManager

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
interface AppComponent : com.ursus.core.di.AppComponent {
    val fooManager: FooManager
}