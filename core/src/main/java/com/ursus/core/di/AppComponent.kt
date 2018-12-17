package com.ursus.core.di

import com.ursus.core.CallManager
import com.ursus.core.NotificationHelper

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 17.12.2018.
 */
interface AppComponent {
    val notifHelper: NotificationHelper
    val callManager: CallManager
}