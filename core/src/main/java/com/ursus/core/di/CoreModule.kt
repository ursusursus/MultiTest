package com.ursus.core.di

import android.content.Context
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
    fun callManager(context: Context, notificationHelper: NotificationHelper) =
        CallManager(context, notificationHelper)

    @Provides @JvmStatic
    fun notificationHelper(context: Context) = NotificationHelper(context)
}