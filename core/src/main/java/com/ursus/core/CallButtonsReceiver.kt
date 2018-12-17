package com.ursus.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ursus.core.di.AppComponent
import com.ursus.core.di.AppComponentProvider

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class CallButtonsReceiver : BroadcastReceiver() {

    private lateinit var callManager: CallManager
    private lateinit var notifHelper: NotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        if (!::callManager.isInitialized) {
            callManager = (context.applicationContext as AppComponentProvider).appComponent<AppComponent>().callManager
        }
        if (!::notifHelper.isInitialized) {
            notifHelper = (context.applicationContext as AppComponentProvider).appComponent<AppComponent>().notifHelper
        }

        if (intent != null) {
            when (intent.action) {
                ACTION_CALL -> {
                    callManager.makeCall()
                    notifHelper.cancelNotification(123)
                }
            }
        }
    }

    companion object {
        val ACTION_CALL = "call"
        val ACTION_ANSWER = "answer"
        val ACTION_DECLINE = "decline"
    }

}