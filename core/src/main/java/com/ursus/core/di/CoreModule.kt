package com.ursus.core.di

import android.app.Application
import com.ursus.core.CallManager
import com.ursus.core.NotificationHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
@Module
object CoreModule {
    @Singleton @Provides @JvmStatic
    fun callManager(application: Application, notificationHelper: NotificationHelper) =
        CallManager(application, notificationHelper)

    @Provides @JvmStatic
    fun notificationHelper(application: Application) = NotificationHelper(application)
}