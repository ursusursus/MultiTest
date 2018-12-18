package com.ursus.core

import android.app.Service
import android.content.Intent
import android.util.Log
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 15.12.2018.
 */
class CallService : Service() {
    @Inject lateinit var notifHelper: NotificationHelper

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        Log.d("Default", "CallService # onCreate")
        foo()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            when (intent.action) {
                ACTION_START -> {
                }
                ACTION_STOP -> stopSelf()
            }
        }
        return Service.START_NOT_STICKY
    }

    private fun foo() {
        Log.d("Default", "foo")
        startForeground(notifHelper.callNotificationId(), notifHelper.immediateForegroundCallNotification().apply {
            Log.d("Default", "notifying immediate")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Default", "CallService # onDestroy")
    }

    override fun onBind(intent: Intent?) = null

    companion object {
        val ACTION_START = "start"
        val ACTION_STOP = "stop"
    }
}