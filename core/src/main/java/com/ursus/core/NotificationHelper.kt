package com.ursus.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 08-Jun-18.
 */
private const val CALL_NOTIFICATION_ID = 1
private const val DEFAULT_CHANNEL_ID: String = "default"
private const val CALL_CHANNEL_ID: String = "call"
private const val MESSAGES_CHANNEL_ID: String = "messages"

class NotificationHelper(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createDefaultChannel()
    }

    private fun createDefaultChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        DEFAULT_CHANNEL_ID,
                        "Default",
                        NotificationManager.IMPORTANCE_DEFAULT
                    ).apply {
                        description = "Default"
                    })
            }
            if (notificationManager.getNotificationChannel(MESSAGES_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        MESSAGES_CHANNEL_ID,
                        "Messages",
                        NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        description = "Messages"
                    })
            }
            if (notificationManager.getNotificationChannel(CALL_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        CALL_CHANNEL_ID,
                        "Calls",
                        NotificationManager.IMPORTANCE_LOW
                    ).apply {
                        description = "Calls"
                    })
            }
        }
    }


    fun showIncomingCallNotification() {
        val declinePendingIntent = createPendingIntent2(Intent(context, CallButtonsReceiver::class.java).apply {
            action = CallButtonsReceiver.ACTION_DECLINE
        })
        val answerPendingIntent = createPendingIntent2(Intent(context, CallButtonsReceiver::class.java).apply {
            action = CallButtonsReceiver.ACTION_ANSWER
        })
        val notif = baseDefaultCallRelatedNotificationBuilder("User 123", "Outgoing call")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .addAction(R.drawable.ic_phone, "Decline", declinePendingIntent)
//            .addAction(R.drawable.ic_phone, "Answer", answerPendingIntent)
            .build()

        Log.d("Default", "notifying")
        notificationManager.notify(callNotificationId(), notif)
    }
//
//    fun outgoingCallNotification(contact: Contact): Notification {
//        return defaultInCallNotificationBuilder(contact, toCallStateString(R.string.outgoing))
//                .addAction(R.drawable.ic_call_decline_notif, context.getString(R.string.call_notif_hangup), hangupCallPendingIntent())
//                .build()
//    }

    fun immediateForegroundCallNotification(): Notification {
        return baseDefaultCallRelatedNotificationBuilder(context.getString(R.string.app_name), "Call pending")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    fun showMissedCallNotification() {
        val callBackPendingIntent = createPendingIntent2(Intent(context, CallButtonsReceiver::class.java).apply {
            action = CallButtonsReceiver.ACTION_CALL
        })

        val notif = baseDefaultCallRelatedNotificationBuilder(context.getString(R.string.app_name), "Missed call")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .addAction(R.drawable.ic_phone, "Call back", callBackPendingIntent)
            .build()

        notificationManager.notify(123, notif)
    }

    private fun baseDefaultCallRelatedNotificationBuilder(
        contentTitle: String,
        contentText: String,
        smallIconRes: Int = R.drawable.ic_phone,
        channelId: String = CALL_CHANNEL_ID
    ): NotificationCompat.Builder {

        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(smallIconRes)
    }

    fun callNotificationId() = CALL_NOTIFICATION_ID

    fun cancelCallNotification() {
        cancelNotification(CALL_NOTIFICATION_ID)
    }

    fun cancelNotification(notifId: Int) {
        notificationManager.cancel(notifId)
    }

//    private fun declineCallPendingIntent() = createPendingIntent(callServiceController.declineCallIntent())
//
//    private fun answerCallPendingIntent() = createPendingIntent(callServiceController.answerCallIntent())
//
//    private fun hangupCallPendingIntent() = createPendingIntent(callServiceController.hangupCallIntent())

//    private fun makeCallPendingIntent(contactJid: String, notifId: Int) =
//        createPendingIntent(callServiceController.makeCallIntent(contactJid, false, notifId))

    private fun createPendingIntent(intent: Intent) =
        PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    private fun createPendingIntent2(intent: Intent) =
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

//    private fun contentCallActivityPendingIntent(): PendingIntent? {
//        // Zakomentovane, lebo toto mi vytvori novu instanciu CallActivity
//        // ale predtym ako destroyne predoslu
//        // -- to sposobi ze onDestroy povodnej aktivity sa zavola po onCreate novej
//        // tzn nova sa attachne, a povodna sa potom detachne, tzn posledny stav je detached texture
//        // Trebalo by tam vymysliet latch count logiku (aby attach cakal na predosly detach)
//        // -- Zatial, singleTask launch mode staci -- po kliku na notifikaciu sa nic nestane, ak uz v nej som
//        // co je vlastne aj lepsie z hladiska perf.
//        // Avsak, ak nebola appka v multitaskingu a vola mi niekto, sa vytvori len CallActivity, a po hangupe
//        // sa vyskoci do launchra (toto je celkom ok), ale ked backpress, tak taktiez sa vyskoci do launchra
//        //
//        // Workaroundy som videl: (zatial neriesim)
//        // FB - robia toto iste, avsak po backpresse otvaraju aktivitu podtym
//        // Instagram - toto sa mi paci najviac, taktiez maju singleTask, tzn po kliku na notif nerobia nic
//        // avsak, pri otvarani call screenu, vtedy replacnu backstack taskbuilderom, tzn placnu mainactivitu podto
//        // pri incoming calle, tzn po backu ju tam maju
//
////        val stackBuilder = TaskStackBuilder.create(context)
////        stackBuilder.addParentStack(MainActivity::class.java)
////        stackBuilder.addNextIntent(Intent(context, MainActivity::class.java))
////        stackBuilder.addNextIntent(Intent(context, CallActivity::class.java))
////        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        return PendingIntent.getActivity(
//            context,
//            0,
//            Intent(context, MainActivity::class.java),
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//    }
//
//    private fun contentMainActivityPendingIntent(
//        requestCode: Int = 0,
//        intentOptions: ((Intent) -> Unit)? = null
//    ): PendingIntent? {
////        val stackBuilder = TaskStackBuilder.create(context)
////        stackBuilder.addParentStack(MainActivity::class.java)
////        stackBuilder.addNextIntent(Intent(context, MainActivity::class.java))
////        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        return PendingIntent.getActivity(
//            context,
//            requestCode,
//            Intent(context, MainActivity::class.java).apply { intentOptions?.invoke(this) },
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//    }
}