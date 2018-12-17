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
class CoreModule {
    @Singleton @Provides fun callManager(context: Context, notificationHelper: NotificationHelper) =
        CallManager(context, notificationHelper)

    @Provides
    fun notificationHelper(context: Context) = NotificationHelper(context)
}