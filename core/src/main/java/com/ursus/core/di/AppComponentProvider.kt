package com.ursus.core.di

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
interface AppComponentProvider {
    fun <T> appComponent() : T
}