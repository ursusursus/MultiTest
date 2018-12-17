package com.ursus.core

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 16.12.2018.
 */
class CallManager(
    private val context: Context,
    private val notificationHelper: NotificationHelper
) {

    private val stateRelay = BehaviorRelay.createDefault<CallState>(CallState.IDLE)
    val stateObservable: Observable<CallState>
        get() = stateRelay.distinctUntilChanged()

    init {
        stateObservable
            .doOnNext { Log.d("Default", "state=$it") }
            .map {
                when (it) {
                    CallState.CONFIRMED,
                    CallState.OUTGOING,
                    CallState.INCOMING -> true
                    else -> false
                }
            }
            .distinctUntilChanged()
            .doOnNext { Log.d("Default", "service=$it") }
            .throwingSubscribe {
                if (it) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(Intent(context, CallService::class.java))
                    } else {
                        context.startService(Intent(context, CallService::class.java))
                    }
                } else {
                    context.stopService(Intent(context, CallService::class.java))
                }
            }

        stateObservable
            .doOnNext { Log.d("Default", "foo=$it") }
            .filter { it == CallState.OUTGOING }
            .throwingSubscribe {
                notificationHelper.showIncomingCallNotification()
            }

//        Observable.just(CallState.INCOMING, CallState.CONFIRMED, CallState.IDLE)
//            .concatMap { callState ->
//                Observable.timer(3, TimeUnit.SECONDS)
//                    .map { callState }
//            }
//            .throwingSubscribe(stateRelay)

    }

    fun makeCall() {
        Observable.just(
            CallState.OUTGOING,
            CallState.CONFIRMED,
            CallState.IDLE
        )
            .publish { o ->
                Observable.merge(
                    o.take(1),
                    o.skip(1).concatMap { callState ->
                        Observable.timer(3, TimeUnit.SECONDS)
                            .map { callState }
                    })
            }
            .doOnNext { Log.d("Default", "bar=$it") }
            .throwingSubscribe(stateRelay)
    }
}

enum class CallState {
    IDLE, INCOMING, OUTGOING, CONFIRMED
}