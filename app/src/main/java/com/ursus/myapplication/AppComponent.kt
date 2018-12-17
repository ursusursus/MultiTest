package com.ursus.myapplication

import android.content.Context
import com.ursus.core.CallManager
import com.ursus.core.NotificationHelper
import com.ursus.sharedlib1.FooManager

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class AppComponent(private val context: Context) :
    com.ursus.core.di.AppComponent,
    com.ursus.sharedlib1.di.AppComponent {

    override val notifHelper
        get() = NotificationHelper(context)

    override val callManager by lazy { CallManager(context, notifHelper) }

    override val fooManager: FooManager
        get() = FooManager()
}