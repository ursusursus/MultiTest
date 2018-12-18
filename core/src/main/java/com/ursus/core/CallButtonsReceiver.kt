package com.ursus.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class CallButtonsReceiver : BroadcastReceiver() {

    @Inject lateinit var callManager: CallManager
    @Inject lateinit var notifHelper: NotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)
        if (context == null) return

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